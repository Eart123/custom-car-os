package com.customcaros.core.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.editTransform
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.UUID

object AppPreferences {

    private const val DATASTORE_NAME = "custom_car_os_preferences.pb"

    private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    private val DASHBOARD_LAYOUT_KEY = stringPreferencesKey("dashboard_layout")
    private val LAST_KNOWN_CITY_KEY = stringPreferencesKey("last_known_city")
    private val UNITS_METRIC_KEY = preferencesKey<Boolean>("units_metric")
    private val AUTO_START_KEY = preferencesKey<Boolean>("auto_start")
    private val VALET_LOCK_ENABLED_KEY = preferencesKey<Boolean>("valet_lock_enabled")
    private val VALET_PIN_KEY = stringPreferencesKey("valet_pin")
    private val SCREEN_TIMEOUT_MINUTES_KEY = preferencesKey<Int>("screen_timeout_minutes")
    private val BLUETOOTH_DEVICE_ADDRESS_KEY = stringPreferencesKey("bluetooth_device_address")
    private val SPOTIFY_CONNECTED_KEY = preferencesKey<Boolean>("spotify_connected")
    private val OFFLINE_MAP_REGIONS_KEY = stringPreferencesKey("offline_map_regions")
    private val VEHICLE_COLOR_KEY = stringPreferencesKey("vehicle_color")
    private val MUSIC_LIBRARY_PATH_KEY = stringPreferencesKey("music_library_path")

    private var context: Context? = null
        @JvmVolatile
        private set

    private val dataStore: DataStore<Preferences> by lazy {
        createDataStore(context = context!!, name = DATASTORE_NAME)
    }

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    suspend fun setThemeMode(mode: String) {
        dataStore.edit { it[THEME_MODE_KEY] = mode }
    }

    val themeMode: Flow<String> = dataStore.data.map {
        it[THEME_MODE_KEY] ?: "dark"
    }

    suspend fun setDashboardLayout(layout: String) {
        dataStore.edit { it[DASHBOARD_LAYOUT_KEY] = layout }
    }

    val dashboardLayout: Flow<String> = dataStore.data.map {
        it[DASHBOARD_LAYOUT_KEY] ?: "split"
    }

    suspend fun setUnitsMetric(metric: Boolean) {
        dataStore.edit { it[UNITS_METRIC_KEY] = metric }
    }

    val unitsMetric: Flow<Boolean> = dataStore.data.map {
        it[UNITS_METRIC_KEY] ?: true
    }

    suspend fun setAutoStart(enabled: Boolean) {
        dataStore.edit { it[AUTO_START_KEY] = enabled }
    }

    val autoStart: Flow<Boolean> = dataStore.data.map {
        it[AUTO_START_KEY] ?: false
    }

    suspend fun setValetLock(enabled: Boolean) {
        dataStore.edit { it[VALET_LOCK_ENABLED_KEY] = enabled }
    }

    val valetLockEnabled: Flow<Boolean> = dataStore.data.map {
        it[VALET_LOCK_ENABLED_KEY] ?: false
    }

    suspend fun setValetPin(pin: String) {
        dataStore.edit { it[VALET_PIN_KEY] = pin }
    }

    val valetPin: Flow<String?> = dataStore.data.map {
        it[VALET_PIN_KEY]
    }

    suspend fun setScreenTimeoutMinutes(minutes: Int) {
        dataStore.edit { it[SCREEN_TIMEOUT_MINUTES_KEY] = minutes }
    }

    val screenTimeoutMinutes: Flow<Int> = dataStore.data.map {
        it[SCREEN_TIMEOUT_MINUTES_KEY] ?: 10
    }

    suspend fun setLastBluetoothAddress(address: String?) {
        dataStore.edit { it[BLUETOOTH_DEVICE_ADDRESS_KEY] = address }
    }

    val lastBluetoothAddress: Flow<String?> = dataStore.data.map {
        it[BLUETOOTH_DEVICE_ADDRESS_KEY]
    }

    suspend fun setSpotifyConnected(connected: Boolean) {
        dataStore.edit { it[SPOTIFY_CONNECTED_KEY] = connected }
    }

    val spotifyConnected: Flow<Boolean> = dataStore.data.map {
        it[SPOTIFY_CONNECTED_KEY] ?: false
    }

    suspend fun setVehicleColor(colorHex: String) {
        dataStore.edit { it[VEHICLE_COLOR_KEY] = colorHex }
    }

    val vehicleColor: Flow<String> = dataStore.data.map {
        it[VEHICLE_COLOR_KEY] ?: "#FFFFFF"
    }

    suspend fun setMusicLibraryPath(path: String) {
        dataStore.edit { it[MUSIC_LIBRARY_PATH_KEY] = path }
    }

    val musicLibraryPath: Flow<String?> = dataStore.data.map {
        it[MUSIC_LIBRARY_PATH_KEY]
    }
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

    fun readBytes(file: File): ByteArray {
        file.inputStream().use { it.readBytes() }
    }

    fun writeBytes(file: File, bytes: ByteArray) {
        val temp = File(file.parentFile, "${file.name}.tmp")
        temp.outputStream().use { it.write(bytes) }
        temp.renameTo(file)
    }
}
