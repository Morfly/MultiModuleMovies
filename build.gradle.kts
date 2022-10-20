plugins {
    id("com.google.devtools.ksp") version versions.ksp apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}