package plugin

import publication.GiniPublicationPlugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

const val GINI_LIB_EXTENSION_NAME = "GiniAndroidLibraryExtension"

class GiniAndroidLibraryPlugin : Plugin<Project> {
    private lateinit var extension: GiniAndroidLibraryExtension

    override fun apply(target: Project): Unit = with(target) {
        applyPlugins()
        createGiniLibExtension()
        applyDefaultAndroidConfiguration()
        setupBuildLogic()
    }

    private fun Project.applyPlugins() = with(plugins) {
        apply(DokkaPlugin::class.java)
        apply(KtlintPlugin::class.java)
        apply(GiniPublicationPlugin::class.java)
    }

    private fun Project.setupBuildLogic() {
        tasks.register<Delete>("cleanOldAars") {
            group = "preBuild"
            doLast {
                fileTree("build/outputs/aar").matching {
                    include("**/*.aar")
                }.forEach { it.delete() }
            }
        }

        tasks.named("preBuild") {
            dependsOn("cleanOldAars")
        }

        tasks.named("build") {
            if (extension.checkCodeStyleOnBuild) {
                dependsOn("ktlintCheck")
            }

            if (extension.generateDocsOnBuild) {
                dependsOn("dokkaHtml")
            }
        }

        extensions.getByType(LibraryExtension::class.java).apply {
            libraryVariants.all {
                outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach {
                        val defaultName = "${project.name}_${it.name}.aar"
                        val artifactName = extension.artifactNamingStrategy(it.name)

                        it.outputFileName = when {
                            artifactName.isBlank() -> defaultName
                            artifactName.endsWith(".aar") -> artifactName
                            else -> "$artifactName.aar"
                        }
                    }
            }
        }
    }

    private fun Project.createGiniLibExtension() = extensions.create(
        GINI_LIB_EXTENSION_NAME,
        GiniAndroidLibraryExtension::class.java,
        this
    ).also { extension = it }
}

internal fun Project.applyDefaultAndroidConfiguration() =
    extensions.getByType(LibraryExtension::class.java).apply {
        val extension =
            extensions.getByName(GINI_LIB_EXTENSION_NAME) as? GiniAndroidLibraryExtension
        println(extension?.toString())
        defaultConfig {
            namespace = extension?.namespace
            compileSdk = extension?.compileSdk
            minSdk = extension?.minSdk

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = "1.8"
        }

        buildFeatures {
            buildConfig = true
        }
    }

internal fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)

fun Project.giniAndroidLibrary(config: GiniAndroidLibraryExtension.() -> Unit) {
    val ext = extensions.getByName(GINI_LIB_EXTENSION_NAME) as? GiniAndroidLibraryExtension
    ext?.apply(config)
    applyDefaultAndroidConfiguration()
}