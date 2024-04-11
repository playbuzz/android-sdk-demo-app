import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.exco.hosttapp.integration"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.exco.hostapp.integration"
        minSdk = 26

        targetSdk = 34

        versionCode = 15
        versionName = "15"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.2"
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(project.findProperty("exco_dev_app_keystore_path") ?: "")
            keyAlias = project.findProperty("exco_dev_app_key_alias") as? String ?: ""
            keyPassword = project.findProperty("exco_dev_app_key_password") as? String ?: ""
            storePassword = project.findProperty("exco_dev_app_store_password") as? String ?: ""
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
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
        viewBinding = true
        buildConfig = true
    }

    applicationVariants.configureEach {
        outputs.configureEach {
            this as com.android.build.gradle.internal.api.ApkVariantOutputImpl

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = formatter.format(Date())
            outputFileName = "ExCo-hostapp_${defaultConfig.versionName}_$date.apk"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

    /** Compose **/
    val composeBom = platform("androidx.compose:compose-bom:2023.10.00")

    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("com.exco:player:1.2.2")
    implementation("com.exco:omsdk-android:1.4.8")
}
