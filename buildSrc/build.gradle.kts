plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.0-alpha02")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
}