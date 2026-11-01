allprojects {
    group = "com.customcaros"
    version = "1.0.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
