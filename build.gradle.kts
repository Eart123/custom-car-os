buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.22")
        classpath("org.jetbrains.kotlin:kotlin-kapt:1.9.22")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}

allprojects {
    group = "com.customcaros"
    version = "1.0.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
