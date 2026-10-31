<?xml version="1.0" encoding="utf-8"?>
<proguard-rules.pro>
# CUSTOM CAR OS — ProGuard rules

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to rules from
# the Android Gradle Plugin.

# Keep Compose
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }

# Keep Room entities and DAOs
-keep class com.customcaros.storage.db.** { *; }
-keep class * extends androidx.room.RoomDatabase

# Keep Media3
-keep class androidx.media3.** { *; }

# Keep Bluetooth classes
-keep class android.bluetooth.** { *; }

# Keep Hilt
-keep class com.customcaros.app.CarOsApplication { *; }
-keep @dagger.hilt.android.HiltAndroidApp class * { *; }

# Keep models
-keep class com.customcaros.core.models.** { *; }

# Do not obfuscate entry points
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.Service