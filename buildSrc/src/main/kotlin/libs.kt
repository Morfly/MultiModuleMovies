@file:Suppress("ClassName")

object versions {
    const val ksp = "1.7.10-1.0.6"
    const val core = "1.8.0"
    const val lifecycle = "2.5.1"
    const val coroutines = "1.6.4"
    const val composeCompiler = "1.3.0"
    const val compose = "1.2.1"
    const val dagger = "2.43.2"
    const val paging = "3.1.1"
    const val pagingCompose = "1.0.0-alpha16"
    const val accompanist = "0.25.1"
    const val retrofit = "2.9.0"
    const val moshi = "1.14.0"
    const val room = "2.4.3"
    const val mockk = "1.12.5"
}

object libs {
    val android = listOf(
        "androidx.core:core-ktx:${versions.core}",
        "androidx.lifecycle:lifecycle-runtime-ktx:${versions.lifecycle}"
    )
    val compose = listOf(
        "androidx.compose.ui:ui:${versions.compose}",
        "androidx.compose.material:material:${versions.compose}",
        "androidx.compose.ui:ui-tooling-preview:${versions.compose}",
        "androidx.activity:activity-compose:1.5.1",
        "androidx.navigation:navigation-compose:2.5.1",
        "io.coil-kt:coil-compose:2.2.0"
    )
    val paging = listOf(
        "androidx.paging:paging-runtime-ktx:${versions.paging}",
        "androidx.paging:paging-compose:${versions.pagingCompose}"
    )
    const val permissionsCompose =
        "com.google.accompanist:accompanist-permissions:${versions.accompanist}"

    const val swipeRefreshCompose =
        "com.google.accompanist:accompanist-swiperefresh:${versions.accompanist}"

    const val palette =
        "androidx.palette:palette-ktx:1.0.0"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}"

    const val dagger =
        "com.google.dagger:dagger:${versions.dagger}"

    const val daggerCompiler =
        "com.google.dagger:dagger-compiler:${versions.dagger}"

    val retrofit = listOf(
        "com.squareup.retrofit2:retrofit:${versions.retrofit}",
        "com.squareup.retrofit2:converter-moshi:${versions.retrofit}",
        "com.squareup.okhttp3:logging-interceptor:4.10.0",
        "com.squareup.moshi:moshi-kotlin:${versions.moshi}",
        "com.squareup.moshi:moshi-adapters:${versions.moshi}",
    )
    val room = listOf(
        "androidx.room:room-ktx:${versions.room}",
        "androidx.room:room-runtime:${versions.room}",
        "androidx.room:room-paging:2.5.0-alpha01",
    )
    const val roomCompiler =
        "androidx.room:room-compiler:${versions.room}"

    val unitTests = listOf(
        "junit:junit:4.13.2",
        "io.mockk:mockk:${versions.mockk}",
        "io.mockk:mockk-agent-jvm:${versions.mockk}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.coroutines}"
    )
    val androidTests = listOf(
        "androidx.test.ext:junit:1.1.3",
        "androidx.test.espresso:espresso-core:3.4.0",
        "androidx.compose.ui:ui-test-junit4:${versions.compose}"
    )
    const val uiautomator =
        "androidx.test.uiautomator:uiautomator:2.2.0"

    const val macroBenchmark =
        "androidx.benchmark:benchmark-macro-junit4:1.1.0-beta04"

    const val profileInstaller =
        "androidx.profileinstaller:profileinstaller:1.2.0"

    val debug = listOf(
        "androidx.compose.ui:ui-tooling:${versions.compose}",
        "androidx.compose.ui:ui-test-manifest:${versions.compose}"
    )
}