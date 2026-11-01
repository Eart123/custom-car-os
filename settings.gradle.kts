pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.2.0"
        id("com.android.library") version "8.2.0"
        id("org.jetbrains.kotlin.android") version "1.9.22"
        id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
        id("org.jetbrains.kotlin.kapt") version "1.9.22"
        id("com.google.dagger.hilt.android") version "2.50"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "custom-car-os"

include(":app")
include(":core")
include(":design-system")
include(":dashboard")
include(":navigation")
include(":offline-maps")
include(":music")
include(":spotify")
include(":bluetooth")
include(":vehicle-visualization")
include(":settings")
include(":weather")
include(":storage")
