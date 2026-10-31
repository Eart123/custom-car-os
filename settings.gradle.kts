pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.kapt") version "1.9.22"
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
