plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    // Precompiled plugin with the base android configuration.
    // Declared in buildSrc/.../android-config.gradle.kts.
    `android-config`
    // Precompiled plugin with the jacoco configuration.
    // Declared in buildSrc/.../jacoco-config.gradle.kts.
    `jacoco-config`
}

android {

    // ===== compose =====
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = versions.composeCompiler
    }
}

dependencies {
    api(project(":movie-search:api"))
    api(project(":movie-details:api"))
    implementation(project(":data:api"))
    implementation(project(":common"))

    // ===== android =====
    implementation(libs.android)
    implementation(libs.paging)

    // ===== compose =====
    implementation(libs.compose)
    implementation(libs.swipeRefreshCompose)

    // ===== kotlin =====
    implementation(libs.coroutines)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    // ===== debug =====
    debugImplementation(libs.debug)

    // ===== tests =====
    testImplementation(libs.unitTests)
    androidTestImplementation(libs.androidTests)
}