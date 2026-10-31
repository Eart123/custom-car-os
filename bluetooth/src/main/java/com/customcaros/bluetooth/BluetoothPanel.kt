package com.customcaros.bluetooth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.customcaros.designsystem.components.AutoCard
import com.customcaros.designsystem.theme.AutomotiveTheme
import kotlinx.coroutines.flow.collectAsState

/**
 * BluetoothPanel — Bluetooth ses çıkışı ve ayar paneli.
 * Gerçek BluetoothManager durumunu okur.
 */
@Composable
fun BluetoothPanel(
    bluetoothManager: BluetoothManager,
    modifier: Modifier = Modifier,
    onPair: () -> Unit = {},
    onDisconnect: () -> Unit = {}
) {
    val connectedDevice by bluetoothManager.connectedDevice.collectAsState()
    val isEnabled by bluetoothManager.isEnabled.collectAsState()
    val isAvailable = bluetoothManager.isBluetoothAvailable()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Bluetooth Ses",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Araç Hoparlörü", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Durum", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    val isConnected = connectedDevice != null
                    AutoChip(
                        label = if (isConnected) "Bağlı" else "Bağlı Değil",
                        color = if (isConnected) Color(0xFF00E676.toInt()) else Color(0xFF4A4A4A.toInt()),
                        contentColor = if (isConnected) Color(0xFF000000.toInt()) else Color(0xFFB0B0B0.toInt())
                    )
                }
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                if (!isAvailable) {
                    Text(
                        text = "Bu cihazda Bluetooth donanımı bulunamadı.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AutoButton(onClick = { if (!isEnabled) bluetoothManager.enable() }, text = "Bluetooth'ı Aç", primary = false)
                        AutoButton(onClick = onPair, text = "Cihaz Eşleştir", primary = false)
                        AutoButton(onClick = onDisconnect, text = "Bağlantıyı Kes", primary = false)
                    }
                }
            }
        }

        AutoCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "A2DP Ses Çıkışı", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Text(
                    text = "Müzik ve navigasyon sesi araç fabrika hoparlörlerine Bluetooth A2DP üzerinden aktarılır.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
