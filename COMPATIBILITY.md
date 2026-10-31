# CUSTOM CAR OS — Compatibility & Platform Documentation

## Minimum Supported Android Version

**minSdkVersion: 21 (Android 5.0 Lollipop)**

### Why not Android 4 (API 14–15)?

The project's technology stack requires APIs introduced in Android 5.0+:

| Library / Feature | Min API | Reason |
|-------------------|---------|--------|
| Jetpack Compose | 21 | Compose runtime requires API 21+ |
| Material 3 | 21 | Material Design 3 components |
| Media3 ExoPlayer | 21 | Media session and playback |
| Room Database | 21 | SQLite with Room abstraction |
| DataStore | 21 | Preferences storage |
| Bluetooth LE / A2DP modern APIs | 21 | BLUETOOTH_CONNECT, BLUETOOTH_SCAN |

Android 4.x (Ice Cream Sandwich, API 14–15) is **not supported**.
Users with Android 4 devices cannot run this application.

## Target Hardware

Primary target: Older Android tablets (e.g., Samsung Galaxy Tab E)

| Specification | Target |
|---------------|--------|
| RAM | 1–2 GB |
| CPU | Older ARM (Cortex-A53 or similar) |
| GPU | Limited OpenGL ES 2.0/3.0 |
| Storage | 8–16 GB |
| Screen | 7–10 inch, landscape |
| Android | 5.0–9.0 |

## Supported Architectures

- armeabi-v7a (32-bit ARM)
- arm64-v8a (64-bit ARM)
- x86 (emulator)
- x86_64 (emulator)

## Dependencies & Licenses

| Dependency | Version | License | Purpose |
|------------|---------|---------|---------|
| Android Gradle Plugin | 8.2.0 | Apache 2.0 | Build system |
| Kotlin | 1.9.22 | Apache 2.0 | Language |
| Jetpack Compose BOM | 2024.01.00 | Apache 2.0 | UI toolkit |
| Material 3 | 1.3.x | Apache 2.0 | Design system |
| Media3 ExoPlayer | 1.3.0 | Apache 2.0 | Media playback |
| Room | 2.6.1 | Apache 2.0 | Local database |
| DataStore | 1.1.1 | Apache 2.0 | Preferences |
| Coil | 2.7.0 | Apache 2.0 | Image loading |
| Mapsforge | 0.19.0 | LGPL 2.1 | Offline maps |
| GraphHopper | 1.0 | Apache 2.0 | Offline routing |
| Filament | 1.24.0 | Apache 2.0 | 3D rendering |

## Network Requests (Documented)

This application makes NO automatic network requests beyond:

1. **Online weather** — Only when user enables online mode and requests weather
2. **Online map tiles** — Only in online mode (offline maps used by default)
3. **Spotify API** — Only when user explicitly connects Spotify
4. **No telemetry, analytics, or advertising requests ever**

## Permissions (Minimal)

| Permission | Purpose | Runtime? |
|------------|---------|----------|
| BLUETOOTH / BLUETOOTH_ADMIN | Bluetooth audio | Yes (Android 12+) |
| BLUETOOTH_CONNECT | Connect to paired devices | Yes |
| BLUETOOTH_SCAN | Discover Bluetooth devices | Yes |
| READ_MEDIA_AUDIO | Scan local music files | Yes |
| ACCESS_FINE_LOCATION | GPS navigation | Yes |
| ACCESS_NETWORK_STATE | Online/offline detection | No (normal) |
| INTERNET | Online maps, weather, Spotify | No (normal) |
| FOREGROUND_SERVICE_MEDIA_PLAYBACK | Background music | No (normal) |
| RECEIVE_BOOT_COMPLETED | Auto-start option | No (normal) |
| WAKE_LOCK | Keep screen on in car dock | No (normal) |
| MODIFY_AUDIO_SETTINGS | Audio focus and volume | Yes |

## Known Limitations

1. **Spotify integration** — Uses Spotify's official APIs where available.
   Spotify offline downloads remain inside the Spotify app and are never
   extracted or copied.

2. **3D Vehicle Visualization** — Current implementation is 2D Compose-based.
   Filament 3D integration requires native .so libraries for each ABI.

3. **Offline Maps** — Mapsforge and GraphHopper require native libraries.
   APK size will increase when offline map support is fully bundled.

4. **Android Auto** — Not implemented. This is a standalone tablet launcher,
   not an Android Auto head-unit app.

5. **Real vehicle data** — All vehicle status (doors, fuel, temperature)
   is simulated. No OBD/CAN interface is integrated.

## Privacy Statement

This application does not:
- Collect analytics or telemetry
- Display advertisements
- Use tracking SDKs
- Transmit personal data to external servers
- Extract Spotify's protected downloads

All user data (music library, navigation destinations, settings) remains
locally on the device.