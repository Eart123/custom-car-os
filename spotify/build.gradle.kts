apply(plugin = "com.android.library")
apply(plugin = "org.jetbrains.kotlin.android")

android {
    namespace = "com.customcaros.spotify"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release { isMinifyEnabled = false }
        debug { isDebuggable = true }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions { jvmTarget = "11" }

    composeOptions { kotlinCompilerExtensionVersion = "1.5.8" }

    buildFeatures { compose = true; buildConfig = true }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)

    implementation(project(":core"))
    implementation(project(":music"))
    implementation(project(":bluetooth"))

    // Spotify integration uses Android MediaSession + official Spotify App Remote / Web API
    // No proprietary scraping. Uses Spotify's official Android SDK where available,
    // otherwise falls back to MediaSession + Intent-based control via the Spotify app.
    implementation(libs.androidx.core.ktx)
}
