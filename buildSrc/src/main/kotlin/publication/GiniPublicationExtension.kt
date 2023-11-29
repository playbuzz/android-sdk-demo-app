package publication

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.kotlin.dsl.provideDelegate
import properties.BooleanProperty
import properties.StringListProperty
import properties.StringProperty
import java.net.URI

open class GiniPublicationExtension(target: Project) {
    var publishVariants by StringListProperty(target, listOf())
    var includeJavadocs by BooleanProperty(target, true)
    var includeSources by BooleanProperty(target, true)
    var groupId by StringProperty(target, "")
    var artifactId by StringProperty(target, "")
    internal var artifactVersion by StringProperty(target, "")
    internal val repository = MavenRepository()
    internal var pomBuilder: MavenPom.() -> Unit = { }

    internal fun isValid() = groupId.isNotBlank() || artifactId.isNotBlank() || artifactVersion.isNotBlank()
}

data class MavenRepository(
    var url: String = "",
    val credentials: Credentials = Credentials()
) {
    val uri get() = URI.create(url)
}

data class Credentials(
    var username: String = "",
    var password: String = ""
)

fun GiniPublicationExtension.repository(config: MavenRepository.() -> Unit) =
    repository.apply(config)

fun MavenRepository.credentials(config: Credentials.() -> Unit) =
    credentials.apply(config)

fun GiniPublicationExtension.pom(pom: MavenPom.() -> Unit) {
    pomBuilder = pom
}