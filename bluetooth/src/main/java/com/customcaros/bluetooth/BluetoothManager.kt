package com.customcaros.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.customcaros.core.models.BluetoothDeviceInfo
import com.customcaros.core.models.ConnectionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * BluetoothManager handles pairing, connection, and A2DP audio routing
 * to the car's factory audio system.
 *
 * Uses Android framework Bluetooth APIs only — no external SDKs.
 */
class BluetoothManager private constructor(
    private val context: Context,
    private val scope: CoroutineScope
) {

    private val bluetoothAdapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter

    private val _devices = MutableStateFlow(emptyList<BluetoothDeviceInfo>())
    val devices: Flow<List<BluetoothDeviceInfo>> = _devices.asStateFlow()

    private val _connectedDevice = MutableStateFlow<BluetoothDeviceInfo?>(null)
    val connectedDevice: Flow<BluetoothDeviceInfo?> = _connectedDevice.asStateFlow()

    private val _isEnabled = MutableStateFlow(bluetoothAdapter?.isEnabled == true)
    val isEnabled: Flow<Boolean> = _isEnabled.asStateFlow()

    private val _isConnecting = MutableStateFlow(false)
    val isConnecting: Flow<Boolean> = _isConnecting.asStateFlow()

    init {
        // Register for Bluetooth state changes
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

        context.registerReceiver(bluetoothReceiver, filter)

        // Observe process lifecycle to unregister
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            androidx.lifecycle.DefaultLifecycleObserver { _, event ->
                if (event == androidx.lifecycle.Event.ON_DESTROY) {
                    context.unregisterReceiver(bluetoothReceiver)
                }
            }
        )
    }

    private val bluetoothReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action ?: return

            when (action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                    _isEnabled.updateAndGet { state == BluetoothAdapter.STATE_ON }
                }
                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let { d ->
                        val info = BluetoothDeviceInfo(
                            name = d.name ?: "Unknown",
                            address = d.address,
                            isConnected = true,
                            isPaired = d.bondState == BluetoothDevice.BOND_BONDED,
                            type = d.type
                        )
                        _connectedDevice.updateAndGet { info }
                        _devices.updateAndGet { list ->
                            list.map { if (it.address == d.address) info else it } + if (list.any { it.address == d.address }) emptyList() else listOf(info)
                        }
                    }
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let { d ->
                        _connectedDevice.updateAndGet { null }
                        _devices.updateAndGet { list ->
                            list.map { if (it.address == d.address) it.copy(isConnected = false) else it }
                        }
                    }
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let { d ->
                        val bonded = d.bondState == BluetoothDevice.BOND_BONDED
                        _devices.updateAndGet { list ->
                            list.map { if (it.address == d.address) it.copy(isPaired = bonded) else it }
                        }
                    }
                }
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let { d ->
                        _devices.updateAndGet { list ->
                            if (list.any { it.address == d.address }) list
                            else list + BluetoothDeviceInfo(
                                name = d.name ?: "Unknown",
                                address = d.address,
                                isConnected = false,
                                isPaired = d.bondState == BluetoothDevice.BOND_BONDED,
                                type = d.type
                            )
                        }
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    _isConnecting.updateAndGet { false }
                }
            }
        }
    }

    fun enable(): Boolean {
        return bluetoothAdapter?.enable() ?: false
    }

    fun disable(): Boolean {
        return bluetoothAdapter?.disable() ?: false
    }

    fun isBluetoothAvailable(): Boolean = bluetoothAdapter != null

    fun startDiscovery() {
        if (bluetoothAdapter?.isDiscovering == true) {
            bluetoothAdapter?.cancelDiscovery()
        }
        _isConnecting.updateAndGet { true }
        bluetoothAdapter?.startDiscovery()
    }

    fun stopDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
        _isConnecting.updateAndGet { false }
    }

    fun pairDevice(device: BluetoothDevice): Boolean {
        // API 21+: createBond() initiates pairing
        return try {
            device.createBond()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun connectDevice(device: BluetoothDevice): Boolean {
        // A2DP connection is managed by the Android Bluetooth stack.
        // We request connection; the system handles audio routing.
        return try {
            device.connectProfile(android.bluetooth.BluetoothProfile.A2DP)
            _connectedDevice.updateAndGet {
                BluetoothDeviceInfo(
                    name = device.name ?: "Unknown",
                    address = device.address,
                    isConnected = true,
                    isPaired = device.bondState == BluetoothDevice.BOND_BONDED,
                    type = device.type
                )
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun disconnectDevice(device: BluetoothDevice): Boolean {
        return try {
            device.disconnectProfile(android.bluetooth.BluetoothProfile.A2DP)
            _connectedDevice.updateAndGet { null }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getConnectedDeviceAddress(): String? = _connectedDevice.value?.address

    fun refreshPairedDevices() {
        val paired = bluetoothAdapter?.bondedDevices?.toList() ?: emptyList()
        _devices.updateAndGet { paired.map { d ->
            BluetoothDeviceInfo(
                name = d.name ?: "Unknown",
                address = d.address,
                isConnected = false,
                isPaired = true,
                type = d.type
            )
        } }
        _isConnecting.updateAndGet { false }
    }

    fun getConnectionStatus(): ConnectionStatus {
        val btDevice = _connectedDevice.value
        return ConnectionStatus(
            isBluetoothConnected = btDevice != null,
            bluetoothDeviceName = btDevice?.name
        )
    }

    fun release() {
        try {
            context.unregisterReceiver(bluetoothReceiver)
        } catch (e: IllegalArgumentException) {
            // Already unregistered
        }
        stopDiscovery()
    }

    companion object {
        @Volatile private var instance: BluetoothManager? = null

        fun init(context: Context, scope: CoroutineScope): BluetoothManager {
            return instance ?: synchronized(this) {
                instance = BluetoothManager(context.applicationContext, scope)
                instance!!
            }
        }

        fun get(): BluetoothManager {
            if (instance == null) {
                throw IllegalStateException("BluetoothManager not initialized. Call init(context, scope) first.")
            }
            return instance!!
        }

        fun destroy() {
            instance?.release()
            instance = null
        }
    }
}
