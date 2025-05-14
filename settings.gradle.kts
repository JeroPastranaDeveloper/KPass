enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("buildlogic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KPass"
include(":app")
include(":core")
include(":core:navigation")
include(":core:designsystem")
include(":core:data")
include(":core:model")
include(":core:domain")
include(":core:utils")
include(":feature")
include(":feature:login")
include(":feature:select-database")
include(":feature:home")
include(":core:viewmodel")
include(":feature:add-edit-account")
include(":core:screen")
