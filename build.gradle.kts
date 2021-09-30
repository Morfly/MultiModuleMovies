// Android and Kotlin Gradle plugins are declared in buildSrc/build.gradle.kts

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}