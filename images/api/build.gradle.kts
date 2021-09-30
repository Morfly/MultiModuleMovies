plugins {
    id("com.android.library")
    id("kotlin-android")
    // Precompiled plugin with the base android configuration.
    // Declared in buildSrc/.../android-config.gradle.kts.
    `android-config`
}

android {

    // ===== compose =====
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

// Versions are declared in gradle/libs.versions.toml
dependencies {

    // Type-safe project accessors
    implementation(projects.common)

    // ===== android =====
    implementation(libs.bundles.android)

    // ===== compose =====
    implementation(libs.bundles.compose)
    debugImplementation(libs.composeTooling)

    // ===== tests =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(libs.composeTest)
}