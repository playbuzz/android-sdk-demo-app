pluginManagement {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
        maven {
            url = uri("https://exco1.jfrog.io/artifactory/android-gradle-release/")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
        maven {
            url = uri("https://exco1.jfrog.io/artifactory/android-gradle-release/")
        }
    }
}

rootProject.name = "ExCo-Integration"
include(":app")
