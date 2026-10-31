buildscript {
    kotlin {
        // JVM target compatible with older Android devices
        jvmToolchain(11)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.kotlin.plugin.compose) apply false
    alias(libs.plugins.hilt.android) apply false
}

allprojects {
    group = "com.customcaros"
    version = "1.0.0"
}

tasks.register("clean", Delete) {
    delete(rootProject.buildDir)
}
