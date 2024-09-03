pluginManagement {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://exco1.jfrog.io/artifactory/android-gradle-release/")
        mavenLocal()
    }
}

rootProject.name = "ExCo-Integration"
include(":app")
