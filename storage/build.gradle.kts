plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.customcaros.storage"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.datastore.preferences)
    kapt(libs.room.compiler)
}
