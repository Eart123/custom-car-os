plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.customcaros.settings"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
        vectorDrawables { useSupportLibrary = true }
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
    implementation(libs.compose.animation)

    implementation(project(":core"))
    implementation(project(":design-system"))
    implementation(project(":storage"))
    implementation(project(":bluetooth"))
}
