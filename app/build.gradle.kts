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
        applicationId = "io.morfly.streaming"
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
        kotlinCompilerExtensionVersion = versions.composeCompiler
    }
}

dependencies {
    implementation(project(":movie-search:impl"))
    implementation(project(":movie-details:impl"))
    implementation(project(":data:impl"))
    implementation(project(":common"))

    // ===== android =====
    implementation(libs.android)

    // ===== compose =====
    implementation(libs.compose)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    // ===== test =====
    testImplementation(libs.unitTests)
    androidTestImplementation(libs.androidTests)

    // ===== debug =====
    debugImplementation(libs.debug)

    // ===== baseline profiles =====
    implementation(libs.profileInstaller)
}