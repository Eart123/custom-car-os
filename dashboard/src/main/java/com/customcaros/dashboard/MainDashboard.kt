package com.customcaros.dashboard

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.theme.AutomotiveTheme
import com.customcaros.designsystem.components.*
import com.customcaros.navigation.NavigationMap
import com.customcaros.bluetooth.BluetoothManager
import com.customcaros.core.network.ConnectionStateHolder
import kotlinx.coroutines.CoroutineScope

/**
 * MainDashboard — ana otomobil arayüzü.
 *
 * Referans görselden esinlenmiş split-screen tasarım:
 * - Üst: Zeit, tarih, internet, Bluetooth, pil
 * - Sol: Harita + hız gösterge + weather widget
 * - Sağ: Spotify medya kontrol paneli
 * - Alt: App dock (Home, Maps, Müzik, Uygulamalar, Bluetooth, Telefon, menü)
 */
@Composable
fun MainDashboard(
    context: Context,
    bluetoothManager: com.customcaros.bluetooth.BluetoothManager,
    scope: CoroutineScope,
    connectionHolder: ConnectionStateHolder,
    modifier: Modifier = Modifier
) {
    var selectedDock by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }

    val dockItems = listOf(
        DockItem(icon = { Icon(Icons.Default.Home, contentDescription = "Ana Sayfa", tint = if (selectedDock == 0) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt())) }, label = "Ana Sayfa"),
        DockItem(icon = { Icon(Icons.Default.Map, contentDescription = "Harita", tint = if (selectedDock == 1) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt())) }, label = "Harita"),
        DockItem(icon = { Icon(Icons.Default.MusicNote, contentDescription = "Müzik", tint = if (selectedDock == 2) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt())) }, label = "Müzik"),
        DockItem(icon = { Icon(Icons.Default.Apps, contentDescription = "Uygulamalar", tint = if (selectedDock == 3) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt())) }, label = "Uygulamalar"),
        DockItem(icon = { Icon(Icons.Default.Bluetooth, contentDescription = "Bluetooth", tint = if (selectedDock == 4) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt())) }, label = "Bluetooth"),
        DockItem(icon = { Icon(Icons.Default.Phone, contentDescription = "Telefon", tint = if (selectedDock == 5) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt())) }, label = "Telefon")
    )

    AutoDashboardLayout(
        modifier = modifier,
        topBarContent = {
            AutoTopBar(
                time = "12:45",
                date = "Paz 31 Eki",
                internetStatus = connectionHolder.isConnected,
                bluetoothStatus = bluetoothManager.connectedDevice.value != null,
                batteryLevel = 85
            )
        },
        appDockContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                // Left side dock items
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dockItems.forEachIndexed { index, item ->
                        AutoDockItem(
                            onClick = { selectedDock = index },
                            icon = item.icon,
                            label = item.label,
                            isSelected = selectedDock == index,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Right side: time + status icons
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "12:45",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = androidx.compose.ui.text.FontWeight.Medium
                            ),
                            color = Color.White
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SignalCellularNull,
                                contentDescription = "Signal",
                                tint = Color(0xFFB0B0B0.toInt()),
                                modifier = Modifier.size(16.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Wifi,
                                contentDescription = "WiFi",
                                tint = Color(0xFFB0B0B0.toInt()),
                                modifier = Modifier.size(16.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Bluetooth,
                                contentDescription = "Bluetooth",
                                tint = if (bluetoothManager.connectedDevice.value != null) Color(0xFF00D4FF.toInt()) else Color(0xFFB0B0B0.toInt()),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Text(
                        text = "Paz 31 Eki",
                        style = MaterialTheme.typography.caption,
                        color = Color(0xFF8E8E8E.toInt())
                    )
                }

                // Menu button
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFB0B0B0.toInt())
                    ),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color(0xFFB0B0B0.toInt())
                    )
                }
            }
        },
        content = {
            // Split screen: Map on left (60%), Spotify on right (40%)
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) {
                // Left: Navigation Map with speed and weather overlay
                AutoCard(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent
                ) {
                    NavigationMap(speedKmh = "0")
                }

                // Right: Spotify media panel
                AutoCard(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                        .padding(start = 8.dp),
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent
                ) {
                    SpotifyIntegration(
                        context = context,
                        onPlayPause = { isPlaying = !isPlaying },
                        onNext = {},
                        onPrevious = {}
                    )
                }
            }
        }
    )
}
