package publication

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import properties.BooleanProperty
import properties.StringListProperty
import properties.StringProperty
import java.net.URI

const val GINI_PUBLICATION_EXTENSION_NAME = "GiniPublicationExtension"

class GiniPublicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.apply("maven-publish")
        val extension = extensions.create<GiniPublicationExtension>(
            GINI_PUBLICATION_EXTENSION_NAME
        )

        val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

        val javadocArtifact: TaskProvider<Jar> by tasks.registering(Jar::class) {
            dependsOn(dokkaHtml)
            archiveClassifier.set("javadoc")
            from(dokkaHtml.outputDirectory)
        }

        val sourcesArtifact: TaskProvider<Jar> by tasks.registering(Jar::class) {
            archiveClassifier.set("sources")
            from(kotlinExtension.sourceSets["main"].kotlin.srcDirs)
        }

        afterEvaluate {
            extensions.getByType(LibraryExtension::class.java).libraryVariants.forEach { variant ->
                if (!extension.isValid()) return@afterEvaluate

                if (extension.publishVariants.isEmpty() || extension.publishVariants.contains(variant.name)) {
                    with(extensions.getByType(PublishingExtension::class.java)) {
                        publications.create(variant.name, MavenPublication::class) {
                            from(components.findByName(variant.name))

                            if (extension.includeJavadocs) {
                                artifact(javadocArtifact)
                            }

                            if (extension.includeSources) {
                                artifact(sourcesArtifact)
                            }

                            groupId = extension.groupId
                            artifactId = extension.artifactId
                            version = extension.artifactVersion

                            pom.apply(extension.pomBuilder)
                        }

                        repositories {
                            maven {
                                url = extension.repository.uri
                                credentials {
                                    username = extension.repository.credentials.username
                                    password = extension.repository.credentials.password
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}