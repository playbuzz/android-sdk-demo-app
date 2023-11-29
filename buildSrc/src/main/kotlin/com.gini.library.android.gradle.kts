import plugin.GiniAndroidLibraryPlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    `maven-publish`
}

apply<GiniAndroidLibraryPlugin>()