@file:Suppress("UnstableApiUsage")

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "jetpack-compose-arch-sample"
include(":app")
include(":images:api", ":images:impl")
include(":profile:api", ":profile:impl")
include(":data:api", ":data:impl")
include(":common")