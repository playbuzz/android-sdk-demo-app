pluginManagement {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

dependencyResolutionManagement {
    val repositoryKey = "android-gradle-dev-local" // "android-gradle-dev-local" // "android-gradle-release-local"
    val baseKey = "publication.exco"
    val artifactoryUsername: String = providers.gradleProperty("$baseKey.consumer.username").get()
    val artifactoryPassword: String = providers.gradleProperty("$baseKey.consumer.password").get()
    val artifactoryContextURL: String = providers.gradleProperty("$baseKey.rootURL").get()

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
//        maven(url = "https://exco1.jfrog.io/artifactory/android-gradle-release/")
        maven {
            url = java.net.URI.create("${artifactoryContextURL}/$repositoryKey")
            credentials {
                username = artifactoryUsername
                password = artifactoryPassword
            }
        }
    }
}

rootProject.name = "ExCo-Integration"
include(":app")
