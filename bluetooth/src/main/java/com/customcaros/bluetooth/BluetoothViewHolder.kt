package com.customcaros.bluetooth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.customcaros.designsystem.components.AutoCard
import com.customcaros.designsystem.theme.AutomotiveTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BluetoothUiState(
    val isEnabled: Boolean = false,
    val isAvailable: Boolean = false,
    val isConnecting: Boolean = false,
    val connectedDevice: com.customcaros.core.models.BluetoothDeviceInfo? = null,
    val pairedDevices: List<com.customcaros.core.models.BluetoothDeviceInfo> = emptyList(),
    val error: String? = null
)

class BluetoothViewHolder(
    private val manager: BluetoothManager,
    private val scope: kotlinx.coroutines.CoroutineScope
) {
    val uiState: StateFlow<BluetoothUiState> =
        kotlinx.coroutines.flow.combine(
            manager.isEnabled,
            manager.isConnecting,
            manager.connectedDevice,
            manager.devices
        ) { isEnabled, isConnecting, connectedDevice, devices ->
            BluetoothUiState(
                isEnabled = isEnabled,
                isAvailable = manager.isBluetoothAvailable(),
                isConnecting = isConnecting,
                connectedDevice = connectedDevice,
                pairedDevices = devices.filter { it.isPaired },
                error = null
            )
        }.stateIn(
            scope = scope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = BluetoothUiState()
        )

    fun toggleBluetooth() {
        if (manager.isEnabled.value) {
            manager.disable()
        } else {
            manager.enable()
        }
    }

    fun startDiscovery() {
        manager.startDiscovery()
    }

    fun stopDiscovery() {
        manager.stopDiscovery()
    }

    fun connect(device: com.customcaros.core.models.BluetoothDeviceInfo) {
        scope.launch {
            // connectDevice requires BluetoothDevice object
            // This is a simplified example; full implementation resolves device
        }
    }

    fun disconnect() {
        manager.connectedDevice.value?.let { device ->
            // resolve device object and disconnect
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1001
    }
}
