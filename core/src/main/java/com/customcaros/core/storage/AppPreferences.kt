package com.customcaros.core.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.File
import java.util.UUID

private val Context.dataStore by preferencesDataStore(name = "custom_car_os_preferences")

object AppPreferences {

    private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    private val DASHBOARD_LAYOUT_KEY = stringPreferencesKey("dashboard_layout")
    private val LAST_KNOWN_CITY_KEY = stringPreferencesKey("last_known_city")
    private val UNITS_METRIC_KEY = booleanPreferencesKey("units_metric")
    private val AUTO_START_KEY = booleanPreferencesKey("auto_start")
    private val VALET_LOCK_ENABLED_KEY = booleanPreferencesKey("valet_lock_enabled")
    private val VALET_PIN_KEY = stringPreferencesKey("valet_pin")
    private val SCREEN_TIMEOUT_MINUTES_KEY = intPreferencesKey("screen_timeout_minutes")
    private val BLUETOOTH_DEVICE_ADDRESS_KEY = stringPreferencesKey("bluetooth_device_address")
    private val SPOTIFY_CONNECTED_KEY = booleanPreferencesKey("spotify_connected")
    private val OFFLINE_MAP_REGIONS_KEY = stringPreferencesKey("offline_map_regions")
    private val VEHICLE_COLOR_KEY = stringPreferencesKey("vehicle_color")
    private val MUSIC_LIBRARY_PATH_KEY = stringPreferencesKey("music_library_path")

    @Volatile
    private var appContext: Context? = null

    private val dataStore: DataStore<Preferences> by lazy {
        appContext!!.dataStore
    }

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    suspend fun setThemeMode(mode: String) {
        dataStore.edit { it[THEME_MODE_KEY] = mode }
    }

    val themeMode: Flow<String>
        get() = dataStore.data.map { it[THEME_MODE_KEY] ?: "dark" }

    suspend fun setDashboardLayout(layout: String) {
        dataStore.edit { it[DASHBOARD_LAYOUT_KEY] = layout }
    }

    val dashboardLayout: Flow<String>
        get() = dataStore.data.map { it[DASHBOARD_LAYOUT_KEY] ?: "split" }

    suspend fun setLastKnownCity(city: String) {
        dataStore.edit { it[LAST_KNOWN_CITY_KEY] = city }
    }

    val lastKnownCity: Flow<String>
        get() = dataStore.data.map { it[LAST_KNOWN_CITY_KEY] ?: "" }

    suspend fun setUnitsMetric(metric: Boolean) {
        dataStore.edit { it[UNITS_METRIC_KEY] = metric }
    }

    val unitsMetric: Flow<Boolean>
        get() = dataStore.data.map { it[UNITS_METRIC_KEY] ?: true }

    suspend fun setAutoStart(enabled: Boolean) {
        dataStore.edit { it[AUTO_START_KEY] = enabled }
    }

    val autoStart: Flow<Boolean>
        get() = dataStore.data.map { it[AUTO_START_KEY] ?: false }

    suspend fun setValetLock(enabled: Boolean) {
        dataStore.edit { it[VALET_LOCK_ENABLED_KEY] = enabled }
    }

    val valetLockEnabled: Flow<Boolean>
        get() = dataStore.data.map { it[VALET_LOCK_ENABLED_KEY] ?: false }

    suspend fun setValetPin(pin: String) {
        dataStore.edit { it[VALET_PIN_KEY] = pin }
    }

    val valetPin: Flow<String?>
        get() = dataStore.data.map { it[VALET_PIN_KEY] }

    suspend fun setScreenTimeoutMinutes(minutes: Int) {
        dataStore.edit { it[SCREEN_TIMEOUT_MINUTES_KEY] = minutes }
    }

    val screenTimeoutMinutes: Flow<Int>
        get() = dataStore.data.map { it[SCREEN_TIMEOUT_MINUTES_KEY] ?: 10 }

    suspend fun setLastBluetoothAddress(address: String?) {
        dataStore.edit {
            if (address == null) it.remove(BLUETOOTH_DEVICE_ADDRESS_KEY)
            else it[BLUETOOTH_DEVICE_ADDRESS_KEY] = address
        }
    }

    val lastBluetoothAddress: Flow<String?>
        get() = dataStore.data.map { it[BLUETOOTH_DEVICE_ADDRESS_KEY] }

    suspend fun setSpotifyConnected(connected: Boolean) {
        dataStore.edit { it[SPOTIFY_CONNECTED_KEY] = connected }
    }

    val spotifyConnected: Flow<Boolean>
        get() = dataStore.data.map { it[SPOTIFY_CONNECTED_KEY] ?: false }

    suspend fun setOfflineMapRegions(regions: String) {
        dataStore.edit { it[OFFLINE_MAP_REGIONS_KEY] = regions }
    }

    val offlineMapRegions: Flow<String?>
        get() = dataStore.data.map { it[OFFLINE_MAP_REGIONS_KEY] }

    suspend fun setVehicleColor(colorHex: String) {
        dataStore.edit { it[VEHICLE_COLOR_KEY] = colorHex }
    }

    val vehicleColor: Flow<String>
        get() = dataStore.data.map { it[VEHICLE_COLOR_KEY] ?: "#FFFFFF" }

    suspend fun setMusicLibraryPath(path: String) {
        dataStore.edit { it[MUSIC_LIBRARY_PATH_KEY] = path }
    }

    val musicLibraryPath: Flow<String?>
        get() = dataStore.data.map { it[MUSIC_LIBRARY_PATH_KEY] }
}

object FileStore {

    fun appFilesDir(context: Context): File =
        context.getExternalFilesDir(null) ?: context.filesDir

    fun offlineMapsDir(context: Context): File {
        val dir = File(appFilesDir(context), "offline_maps")
        dir.mkdirs()
        return dir
    }

    fun musicDir(context: Context): File {
        val dir = File(appFilesDir(context), "music")
        dir.mkdirs()
        return dir
    }

    fun uniqueName(prefix: String, extension: String): String =
        "$prefix-${UUID.randomUUID()}$extension"

    fun readBytes(file: File): ByteArray = file.inputStream().use { it.readBytes() }

    fun writeBytes(file: File, bytes: ByteArray) {
        val temp = File(file.parentFile, "${file.name}.tmp")
        temp.outputStream().use { it.write(bytes) }
        temp.renameTo(file)
    }
}
