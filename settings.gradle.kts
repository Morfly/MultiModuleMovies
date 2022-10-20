@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "movies-compose-multi-module"

include(":app")
include(":movie-search:api")
include(":movie-search:impl")
include(":movie-details:api")
include(":movie-details:impl")
include(":data:api")
include(":data:impl")
include(":common")

include(":benchmark")
