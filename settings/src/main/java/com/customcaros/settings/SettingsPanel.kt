package com.customcaros.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WidthFull
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.components.*
import com.customcaros.designsystem.theme.AutomotiveTheme
import com.customcaros.bluetooth.BluetoothViewHolder

/**
 * SettingsPanel — automotive settings with privacy focus.
 * No telemetry, no analytics, no data collection.
 */
@Composable
fun SettingsPanel(
    bluetoothHolder: BluetoothViewHolder,
    modifier: Modifier = Modifier
) {
    var darkTheme by remember { mutableStateOf(true) }
    var autoStart by remember { mutableStateOf(false) }
    var valetLock by remember { mutableStateOf(false) }
    var unitsMetric by remember { mutableStateOf(true) }
    var screenTimeout by remember { mutableStateOf(10) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Display section
        Text("Display", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 8.dp))
        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                AutoStatusRow(
                    label = "Theme",
                    value = "Dark",
                    icon = { Icon(Icons.Default.DarkMode, contentDescription = "Theme", tint = MaterialTheme.colorScheme.primary) }
                )
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                AutoStatusRow(
                    label = "Units",
                    value = if (unitsMetric) "Metric" else "Imperial",
                    icon = { Icon(Icons.Default.WidthFull, contentDescription = "Units", tint = MaterialTheme.colorScheme.primary) }
                )
            }
        }

        // Startup section
        Text("Startup", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 8.dp))
        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Auto-start on boot", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    AutoSwitch(checked = autoStart, onCheckedChange = { autoStart = it })
                }
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Valet / Privacy lock", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    AutoSwitch(checked = valetLock, onCheckedChange = { valetLock = it })
                }
            }
        }

        // Bluetooth section
        Text("Bluetooth Audio", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 8.dp))
        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                val connected = bluetoothHolder.uiState.value.connectedDevice != null
                AutoStatusRow(
                    label = "Bluetooth",
                    value = if (connected) "Connected" else "Not connected",
                    valueColor = if (connected) Color(0xFF00E676.toInt()) else Color(0xFFFF5252.toInt()),
                    icon = { Icon(Icons.Default.Bluetooth, contentDescription = "Bluetooth", tint = MaterialTheme.colorScheme.primary) }
                )
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                AutoButton(onClick = { bluetoothHolder.startDiscovery() }, text = "Pair New Device")
            }
        }

        // Privacy section
        Text("Privacy", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 8.dp))
        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "This app does not collect, transmit, or store any personal data beyond what is stored locally on your device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Text(
                    text = "No analytics • No telemetry • No advertising • No tracking",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }

        // Screen timeout
        AutoCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Screen timeout (minutes)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = "$screenTimeout", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}
