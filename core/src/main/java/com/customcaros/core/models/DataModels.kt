package com.customcaros.core.models

import java.io.Serializable

/**
 * Represents a locally stored music track.
 * Privacy: metadata only stays on-device.
 */
data class MusicTrack(
    val id: String,
    val title: String,
    val artist: String?,
    val album: String?,
    val filePath: String,
    val durationMs: Long,
    val sizeBytes: Long,
    val albumArtPath: String?,
    val dateAdded: Long = System.currentTimeMillis()
) : Serializable

/**
 * Represents a playlist of local tracks.
 */
data class Playlist(
    val id: String,
    val name: String,
    val trackIds: MutableList<String> = mutableListOf(),
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

/**
 * Represents a saved destination for navigation.
 */
data class SavedDestination(
    val id: String,
    val label: String,
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

/**
 * Represents vehicle state (simulated unless real OBD/CAN data available).
 * All fields marked as simulated unless actual vehicle interface is present.
 */
data class VehicleState(
    val isLocked: Boolean = true,
    val doorOpen: Boolean = false,
    val trunkOpen: Boolean = false,
    val fuelLevelPercent: Int = 100,        // simulated
    val engineTemperatureC: Int? = null,     // simulated
    val isEngineRunning: Boolean = false,    // simulated
    val outsideTemperatureC: Int? = null,    // from weather service
    val speedKmh: Double = 0.0,              // from GPS
    val odometerKm: Double = 0.0,            // simulated
    val batteryVoltage: Double? = null,      // simulated
    val headlightsOn: Boolean = false,
    val colorHex: String = "#FFFFFF"
) {
    /**
     * Returns true if this state contains real vehicle data (from OBD/CAN)
     * vs simulated demo data.
     */
    val isSimulated: Boolean
        get() = true // Default: all values simulated until real interface added
}

/**
 * Represents weather information.
 */
data class WeatherInfo(
    val location: String,
    val temperatureC: Double,
    val condition: String,        // e.g. "Clear", "Rain", "Cloudy"
    val iconCode: String?,        // weather icon identifier
    val humidityPercent: Int,
    val windSpeedKmh: Double,
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Represents a Bluetooth audio device.
 */
data class BluetoothDeviceInfo(
    val name: String,
    val address: String,
    val isConnected: Boolean = false,
    val isPaired: Boolean = false,
    val type: Int = android.bluetooth.BluetoothDevice.DEVICE_TYPE_UNKNOWN
)

/**
 * Network connectivity result for the app.
 */
data class ConnectionStatus(
    val isInternetAvailable: Boolean = false,
    val isWifiConnected: Boolean = false,
    val isMobileConnected: Boolean = false,
    val isBluetoothConnected: Boolean = false,
    val bluetoothDeviceName: String? = null
)