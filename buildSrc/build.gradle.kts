plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.library:com.android.library.gradle.plugin:8.1.2")
    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.9.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.0")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:11.6.0")
}