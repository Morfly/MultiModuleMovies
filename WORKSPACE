workspace(name = "jetpack-compose-arch-sample")

KOTLIN_COMPILER_VERSION = "1.5.10"
COMPOSE_VERSION = "1.0.0"

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JAVA_VERSION = "4.0.0"
RULES_JAVA_SHA = "34b41ec683e67253043ab1a3d1e8b7c61e4e8edefbcad485381328c934d072fe"

http_archive(
    name = "rules_java",
    sha256 = RULES_JAVA_SHA,
    url = "https://github.com/bazelbuild/rules_java/releases/download/{v}/rules_java-{v}.tar.gz".format(v = RULES_JAVA_VERSION),
)

load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")
rules_java_dependencies()
rules_java_toolchains()

DAGGER_TAG = "2.36"
DAGGER_SHA = "1e6d5c64d336af2e14c089124bf2bd9d449010db02534ce820abc6a7f0429c86"

http_archive(
    name = "dagger",
    sha256 = DAGGER_SHA,
    strip_prefix = "dagger-dagger-%s" % DAGGER_TAG,
    urls = ["https://github.com/google/dagger/archive/dagger-%s.zip" % DAGGER_TAG],
)

load("@dagger//:workspace_defs.bzl", "DAGGER_ARTIFACTS", "DAGGER_REPOSITORIES")

RULES_JVM_EXTERNAL_VERSION = "4.1"
RULES_JVM_EXTERNAL_SHA = "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_VERSION,
    urls = ["https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_VERSION],
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = DAGGER_ARTIFACTS + [
        "androidx.activity:activity-compose:1.3.0",
        "androidx.appcompat:appcompat:1.3.0",
        "androidx.core:core-ktx:1.5.0",
        "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1",
        "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07",
        "androidx.navigation:navigation-compose:2.4.0-alpha05",
        "androidx.paging:paging-compose:1.0.0-alpha12",
        "androidx.paging:paging-runtime-ktx:3.0.0",
        "androidx.palette:palette-ktx:1.0.0",
        "androidx.room:room-compiler:2.3.0",
        "androidx.room:room-ktx:2.3.0",
        "androidx.room:room-runtime:2.3.0",
        "com.google.android.material:material:1.3.0",
        "com.squareup.moshi:moshi-kotlin:1.12.0",
        "com.squareup.okhttp3:logging-interceptor:4.2.1",
        "com.squareup.retrofit2:converter-moshi:2.9.0",
        "com.squareup.retrofit2:retrofit:2.9.0",
        "io.coil-kt:coil-compose:1.3.1",
        "androidx.compose.ui:ui:%s" % COMPOSE_VERSION,
        "androidx.compose.ui:ui-tooling:%s" % COMPOSE_VERSION,
        "androidx.compose.material:material:%s" % COMPOSE_VERSION,
        "androidx.compose.compiler:compiler:%s" % COMPOSE_VERSION,
        "androidx.compose.runtime:runtime:%s" % COMPOSE_VERSION,
        "com.google.guava:guava:28.1-android",
    ],
    repositories = DAGGER_REPOSITORIES + [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
    override_targets = {
        "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm": "@//third_party:kotlinx_coroutines_core_jvm",
        "org.jetbrains.kotlin:kotlin-reflect": "@//third_party:kotlin_reflect",
        "androidx.room:room-runtime": "@//third_party:room_runtime",
    },
)

maven_install(
    name = "maven_secondary",
    artifacts = [
        "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.1",
        "androidx.room:room-runtime:2.3.0",
    ],
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)


RULES_ANDROID_VERSION = "0.1.1"
RULES_ANDROID_SHA = "cd06d15dd8bb59926e4d65f9003bfc20f9da4b2519985c27e190cddc8b7a7806"

http_archive(
    name = "rules_android",
    sha256 = RULES_ANDROID_SHA,
    strip_prefix = "rules_android-%s" % RULES_ANDROID_VERSION,
    urls = ["https://github.com/bazelbuild/rules_android/archive/v%s.zip" % RULES_ANDROID_VERSION],
)

load("@rules_android//android:rules.bzl", "android_sdk_repository")

android_sdk_repository(
    name = "androidsdk",
    api_level = 29,
    build_tools_version = "29.0.3",
)

RULES_KOTLIN_VERSION = "c26007a1776a79d94bea7c257ef07a23bbc998d5"
RULES_KOTLIN_SHA = "be7b1fac4f93fbb81eb79f2f44caa97e1dfa69d2734e4e184443acd9f5182386"

http_archive(
    name = "io_bazel_rules_kotlin",
    sha256 = RULES_KOTLIN_SHA,
    strip_prefix = "rules_kotlin-%s" % RULES_KOTLIN_VERSION,
    urls = ["https://github.com/bazelbuild/rules_kotlin/archive/%s.tar.gz" % RULES_KOTLIN_VERSION],
)

load("@io_bazel_rules_kotlin//kotlin:dependencies.bzl", "kt_download_local_dev_dependencies")

kt_download_local_dev_dependencies()

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories")

RULES_KOTLIN_COMPILER_RELEASE = {
    "urls": ["https://github.com/JetBrains/kotlin/releases/download/v{v}/kotlin-compiler-{v}.zip".format(v = KOTLIN_COMPILER_VERSION)],
    "sha256": "2f8de1d73b816354055ff6a4b974b711c11ad55a68b948ed30b38155706b3c4e",
}

kotlin_repositories(compiler_release = RULES_KOTLIN_COMPILER_RELEASE)

register_toolchains("//:kotlin_toolchain")