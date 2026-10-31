# CUSTOM CAR OS — Privacy Statement

## Data Collection

This application collects **zero** personal data.

- No analytics SDKs (no Firebase Analytics, no Google Analytics, no Mixpanel, etc.)
- No telemetry
- No advertising SDKs (no AdMob, no Facebook Ads, no Unity Ads, etc.)
- No user tracking
- No profiling
- No device fingerprinting

## Data Storage

All data is stored **locally on your device** only:

- Music library metadata (stored in Room database)
- Navigation destinations (stored in Room database)
- App settings and preferences (stored in DataStore)
- Offline map regions (stored in app's external files directory)

No data is ever transmitted to external servers.

## Network Access

The application requests `android.permission.INTERNET` only for:

1. Online map tiles (when online mode is active)
2. Weather information (when online mode is active)
3. Spotify API communication (when user explicitly connects Spotify)

You can disable all network access by using the app in offline mode.

## Spotify Integration

Spotify integration uses Spotify's official Android APIs only:

- No scraping of Spotify's web or mobile interfaces
- No bypassing of Spotify's authentication, subscription, or DRM
- No extraction or copying of Spotify's protected offline downloads
- Spotify offline downloads remain exclusively inside the Spotify app

## Vehicle Data

All vehicle information displayed (door status, fuel level, engine
temperature, lock status, etc.) is **simulated** unless an actual
OBD/CAN vehicle interface is connected. No real vehicle sensor data
is collected or transmitted.

## Permissions

All permissions are runtime-requested and used only for local
functionality:

- Bluetooth: to connect to your car's factory audio system
- Location: for GPS navigation
- Media: to scan your local music files
- Network: for online maps, weather, and Spotify (optional)

## Contact

For privacy questions, review this document. No personal data is
collected, so there is no data to delete or export.