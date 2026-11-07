package com.customcaros.core.repository

import com.customcaros.core.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository for music data (local files only).
 * Privacy: no cloud, no scanning of user's personal data beyond music folder.
 */
class MusicRepository {

    private val _tracks = MutableStateFlow(emptyList<MusicTrack>())
    val tracks: Flow<List<MusicTrack>> = _tracks.asStateFlow()

    private val _playlists = MutableStateFlow(emptyList<Playlist>())
    val playlists: Flow<List<Playlist>> = _playlists.asStateFlow()

    fun setTracks(tracks: List<MusicTrack>) {
        _tracks.update { tracks }
    }

    fun setPlaylists(playlists: List<Playlist>) {
        _playlists.update { playlists }
    }

    fun addTrackToPlaylist(playlistId: String, trackId: String) {
        _playlists.update { playlists ->
            playlists.map { p ->
                if (p.id == playlistId && !p.trackIds.contains(trackId)) {
                    p.copy(trackIds = (p.trackIds + trackId).toMutableList())
                } else p
            }
        }
    }

    fun removeTrackFromPlaylist(playlistId: String, trackId: String) {
        _playlists.update { playlists ->
            playlists.map { p ->
                if (p.id == playlistId) {
                    p.copy(trackIds = (p.trackIds - trackId).toMutableList())
                } else p
            }
        }
    }
}

/**
 * Repository for navigation / saved destinations.
 */
class NavigationRepository {

    private val _destinations = MutableStateFlow(emptyList<SavedDestination>())
    val destinations: Flow<List<SavedDestination>> = _destinations.asStateFlow()

    fun setDestinations(destinations: List<SavedDestination>) {
        _destinations.update { destinations }
    }

    fun addDestination(dest: SavedDestination) {
        _destinations.update { list -> list + dest }
    }

    fun removeDestination(id: String) {
        _destinations.update { list -> list.filter { it.id != id } }
    }
}

/**
 * Repository for vehicle state (simulated).
 */
class VehicleRepository {

    private val _state = MutableStateFlow(VehicleState())
    val state: Flow<VehicleState> = _state.asStateFlow()

    fun updateState(updater: (VehicleState) -> VehicleState) {
        _state.update { updater(it) }
    }

    fun setSimulatedSpeed(kmh: Double) {
        _state.update { it.copy(speedKmh = kmh) }
    }

    fun toggleHeadlights() {
        _state.update { it.copy(headlightsOn = !it.headlightsOn) }
    }

    fun toggleLock() {
        _state.update { it.copy(isLocked = !it.isLocked) }
    }

    fun setDoorOpen(open: Boolean) {
        _state.update { it.copy(doorOpen = open) }
    }

    fun setColor(colorHex: String) {
        _state.update { it.copy(colorHex = colorHex) }
    }
}

/**
 * Repository for Bluetooth device state.
 */
class BluetoothRepository {

    private val _devices = MutableStateFlow(emptyList<BluetoothDeviceInfo>())
    val devices: Flow<List<BluetoothDeviceInfo>> = _devices.asStateFlow()

    private val _connectedDevice = MutableStateFlow<BluetoothDeviceInfo?>(null)
    val connectedDevice: Flow<BluetoothDeviceInfo?> = _connectedDevice.asStateFlow()

    fun setDevices(devices: List<BluetoothDeviceInfo>) {
        _devices.update { devices }
    }

    fun setConnectedDevice(device: BluetoothDeviceInfo?) {
        _connectedDevice.update { device }
    }

    fun updateConnectionStatus(address: String, connected: Boolean) {
        _devices.update { list ->
            list.map { d ->
                if (d.address == address) d.copy(isConnected = connected) else d
            }
        }
        if (connected) {
            _connectedDevice.update { _devices.value.firstOrNull { it.address == address } }
        } else {
            _connectedDevice.update { null }
        }
    }
}

/**
 * Repository for weather data.
 */
class WeatherRepository {

    private val _weather = MutableStateFlow<WeatherInfo?>(null)
    val weather: Flow<WeatherInfo?> = _weather.asStateFlow()

    fun updateWeather(weather: WeatherInfo) {
        _weather.update { weather }
    }

    fun clearWeather() {
        _weather.update { null }
    }
}
