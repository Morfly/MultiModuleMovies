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
        kotlinCompilerExtensionVersion = versions.composeCompiler
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":movie-details:api"))

    // ===== android =====
    implementation(libs.android)

    // ===== compose =====
    implementation(libs.compose)
    debugImplementation(libs.debug)

    // ===== tests =====
    testImplementation(libs.unitTests)
    androidTestImplementation(libs.androidTests)
}