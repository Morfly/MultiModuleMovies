plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    // Plugins are declared in gradle/libs.versions.toml
    // Precompiled plugin with the base android configuration.
    // Declared in buildSrc/.../android-config.gradle.kts.
    `android-config`
}

// Versions are declared in gradle/libs.versions.toml
dependencies {

    // Type-safe project accessors
    api(projects.data.api)
    implementation(projects.common)

    // ===== android =====
    implementation(libs.bundles.android)
    implementation(libs.paging)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    // ===== retrofit =====
    implementation(libs.bundles.retrofit)

    // ===== room =====
    implementation(libs.bundles.room)
    kapt(libs.roomCompiler)

    // ===== tests =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)
}