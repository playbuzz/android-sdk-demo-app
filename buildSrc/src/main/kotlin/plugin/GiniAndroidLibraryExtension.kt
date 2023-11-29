package plugin

import publication.GiniPublicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.dokka.gradle.DokkaTask
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import properties.BooleanProperty
import properties.IntProperty
import properties.StringProperty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class GiniAndroidLibraryExtension(val target: Project) {
    var namespace by StringProperty(target, "com.example.namespace")
    var libraryVersion by StringProperty(target, "1.0")
    var minSdk by IntProperty(target, 26)
    var compileSdk by IntProperty(target, 33)
    var generateDocsOnBuild by BooleanProperty(target, true)
    var checkCodeStyleOnBuild by BooleanProperty(target, true)

    internal var artifactNamingStrategy: (String) -> String = { variantName ->
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.format(Date())
        "${target.rootProject.name}_${variantName}_${libraryVersion}_$date.aar"
    }
}

fun GiniAndroidLibraryExtension.dokka(config: DokkaTask.() -> Unit) = with(target) {
    tasks.withType(DokkaTask::class.java).forEach(config)
}

fun GiniAndroidLibraryExtension.publishing(config: GiniPublicationExtension.() -> Unit) = with(target) {
    configure<GiniPublicationExtension> {
        apply(config)
        artifactVersion = libraryVersion
    }
}

fun GiniAndroidLibraryExtension.ktlint(config: KtlintExtension.() -> Unit) = with(target) {
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        apply(config)

        android.set(true)
    }
}

fun GiniAndroidLibraryExtension.artifactNamingStrategy(nameGenerator: (variantName: String) -> String) {
    artifactNamingStrategy = nameGenerator
}