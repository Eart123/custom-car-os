# SDD ledger — plan: CUSTOM CAR OS Android Automotive Launcher

# Project: CUSTOM CAR OS — Production-Ready Android Automotive Launcher
# Inspired by Vivid Car Launcher and Tesla infotainment interface
# Target: Android 5.0+ (API 21), optimized for older tablets (Samsung Galaxy Tab E)
# Privacy-first: no analytics, no telemetry, no ads, no tracking

# Global constraints:
# - Kotlin + Jetpack Compose + Material 3
# - Multi-module architecture
# - No proprietary code/assets copied from Vivid
# - Offline-first with graceful online mode
# - Spotify: media key control, no API key needed
# - Bluetooth A2DP audio output
# - 2D vehicle visualization (Compose-based, Filament optional)

# Module list:
# app, core, design-system, dashboard, navigation, offline-maps,
# music, spotify, bluetooth, vehicle-visualization, settings, weather, storage

# Rulings:
# Task 1: Ruling: minSdk 21 (Android 5.0) — Jetpack Compose, Material 3, Media3,
# Room, DataStore all require API 21+. Android 4 (API 14-15) is not compatible.
# Task 2: Ruling: Spotify uses Android media key events (no API key, no scraping).
# Task 3: Ruling: Filament removed — Maven artifact unavailable; 2D Compose used.
# Task 4: Ruling: Build errors fixed — kapt added, manifest permissions fixed,
# CarOsApplication package fixed, gradlew restored, unused imports removed.

# Pre-flight: no shared interfaces (fresh project)

# Tasks completed:
# - Project scaffold: settings.gradle.kts, build.gradle.kts, gradle.properties, libs.versions.toml
# - 13 module build.gradle.kts files created
# - Core: DI module, NetworkState, AppPreferences (DataStore), DataModels, Repositories, ConnectivityManagerObserver
# - Design system: AutomotiveTheme, AutoComponents, AutoLayout, AutoAppDock
# - Storage: Room entities, AppDatabase, StorageFacade, DAOs
# - Music: LocalMusicPlayer (Media3 ExoPlayer), MusicPlayerPanel, CarMusicSessionService
# - Bluetooth: BluetoothManager, BluetoothPanel (real state), BluetoothViewHolder
# - Vehicle visualization: Renault Symbol 2D + 20AUR23 plate + door/light simulation
# - Dashboard: MainDashboard split-screen (map left 60%, Spotify right 40%), AutoAppDock bottom bar
# - Navigation: NavigationMap with speed overlay, weather overlay, zoom controls
# - Spotify: SpotifyIntegration (media key control, album art, library chips)
# - Settings: SettingsPanel (theme, units, auto-start, valet lock, Bluetooth, privacy)
# - Weather: WeatherWidget (premium card with day/date/temp/condition)
# - Offline maps: OfflineMapsManager (connection status, region download UI)
# - App: MainActivity, BootReceiver, CarOsApplication, AndroidManifest.xml, resources
# - Gradle wrapper: gradlew, gradlew.bat, gradle-wrapper.properties (Gradle 8.2)
# - GitHub Actions: .github/workflows/android.yml (cloud build, no Android Studio needed)
# - Documentation: README.md, BUILD_INSTRUCTIONS.md, FEATURE_CHECKLIST.md, COMPATIBILITY.md, PRIVACY.md

# Implementation complete. Ready for GitHub upload and Gradle build via GitHub Actions.
# REA MCP: jadx-headless-mcp-0.8.0-all.jar present, but REA requires 0.7.1 specifically.
# Version mismatch prevents REA analysis. Need 0.7.1 JAR for REA APK decompilation.