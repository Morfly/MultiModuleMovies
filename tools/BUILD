load("@rules_java//java:defs.bzl", "java_plugin", "java_library")
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_compiler_plugin")
load("@rules_jvm_external//:defs.bzl", "artifact")

package(default_visibility = ["//visibility:public"])

kt_compiler_plugin(
    name = "jetpack_compose_compiler_plugin",
    deps = [artifact("androidx.compose.compiler:compiler")],
    id = "androidx.compose.compiler",
    target_embedded_compiler = True,
)

java_plugin(
    name = "androidx_room_room_compiler_plugin",
    processor_class = "androidx.room.RoomProcessor",
    deps = [artifact("androidx.room:room-compiler")],
    visibility = ["//visibility:private"],
    neverlink = True,
)

java_library(
    name = "androidx_room_room_compiler_library",
    exported_plugins = [":androidx_room_room_compiler_plugin"],
    neverlink = True,
)