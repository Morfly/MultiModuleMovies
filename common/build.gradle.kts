plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
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

    // ===== android =====
    implementation(libs.bundles.android)
    implementation(libs.palette)
    implementation(libs.paging)

    // ===== compose =====
    implementation(libs.bundles.compose)
    debugImplementation(libs.composeTooling)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    // ===== tests ======
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(libs.composeTest)
}