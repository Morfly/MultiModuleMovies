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
        kotlinCompilerExtensionVersion = versions.composeCompiler
    }
}

dependencies {

    // ===== android =====
    implementation(libs.android)
    implementation(libs.paging)
    implementation(libs.palette)

    // ===== compose =====
    implementation(libs.compose)

    // ===== kotlin =====
    implementation(libs.coroutines)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)
}