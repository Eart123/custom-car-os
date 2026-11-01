plugins {
    id("com.android.library") version "8.2.0"
    id("org.jetbrains.kotlin.android") version "1.9.22"
}

android {
    namespace = "com.customcaros.vehiclevisualization"
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
    implementation(libs.compose.animation)

    implementation(project(":core"))
    implementation(project(":design-system"))

    // Filament 3D engine kaldırıldı — Maven'de uygun artifact bulunamadı.
    // 2D Compose tabanlı araç görselleştirme kullanılıyor.
    // İleride Filament native .so dosyaları eklenirse 3D entegrasyonu yapılabilir.
}
