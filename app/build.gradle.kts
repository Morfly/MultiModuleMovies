plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    // Precompiled plugin with the base android configuration.
    // Declared in buildSrc/.../android-config.gradle.kts.
    `android-config`
}

android {
    defaultConfig {
        applicationId = "com.morfly.sample.archcompose"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        named("debug") {
            storeFile = rootProject.file("debug.keystore")
        }
    }

    // ===== compose =====
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}


// Versions are declared in gradle/libs.versions.toml
dependencies {

    // Type-safe project accessors
    implementation(projects.images.impl)
    implementation(projects.profile.impl)
    implementation(projects.data.impl)
    implementation(projects.common)

    // ===== android =====
    implementation(libs.bundles.android)

    // ===== compose =====
    implementation(libs.bundles.compose)
    debugImplementation(libs.composeTooling)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    // ===== tests =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(libs.composeTest)
}