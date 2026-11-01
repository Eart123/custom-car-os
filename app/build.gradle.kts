apply { plugin("com.android.application") }
apply { plugin("org.jetbrains.kotlin.android") }
apply { plugin("org.jetbrains.kotlin.plugin.serialization") }
apply { plugin("org.jetbrains.kotlin.kapt") }

android {
    namespace = "com.customcaros.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.customcaros.app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.animation)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(project(":core"))
    implementation(project(":design-system"))
    implementation(project(":dashboard"))
    implementation(project(":navigation"))
    implementation(project(":offline-maps"))
    implementation(project(":music"))
    implementation(project(":spotify"))
    implementation(project(":bluetooth"))
    implementation(project(":vehicle-visualization"))
    implementation(project(":settings"))
    implementation(project(":weather"))
    implementation(project(":storage"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.datastore.preferences)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.session)

    debugImplementation(libs.compose.ui.tooling.preview)
}
