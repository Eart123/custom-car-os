pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.android") version "1.9.23"
        id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
        id("org.jetbrains.kotlin.kapt") version "1.9.23"
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useModule("org.jetbrains.kotlin:${requested.id.name}:1.9.23")
            }
        }
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
