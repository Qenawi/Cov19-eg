import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Libraries.Apps.compileSdk)
    defaultConfig {
        minSdkVersion(Libraries.Apps.minSdk)
        targetSdkVersion(Libraries.Apps.targetSdk)
        versionCode = Libraries.Apps.versionCode
        versionName = Libraries.Apps.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$applicationId-v$versionName($versionCode)")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    dataBinding { isEnabled = true }
    androidExtensions { isExperimental=true }
    compileOptions{
        sourceCompatibility=JavaVersion.VERSION_1_8
        targetCompatibility=JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.Libs.kotlin)
    implementation(Libraries.Libs.appcompat)
    implementation(Libraries.constraintlayout)
    implementation(Libraries.Dagger.dagger)
    implementation(Libraries.Dagger.androidSupport)
    implementation(Libraries.Coroutines.android)
    implementation(Libraries.Coroutines.core)
    kapt(Libraries.Dagger.compiler)
    kapt(Libraries.Dagger.androidProcessor)
    implementation(Libraries.Fragment.fragment)
    implementation(Libraries.Lifecycle.extensions)
    implementation(Libraries.Glide.library)
    kapt(Libraries.Glide.compiler)
    implementation(Libraries.Google.playServiceBase)
    implementation(Libraries.Google.playServiceBasement)
    implementation(Libraries.Google.playServiceLocation)
    implementation(Libraries.Google.playServiceGcm)
    implementation(Libraries.Google.playServiceMap)
    implementation(Libraries.FireBase.fireStore)
    implementation(Libraries.FireBase.messeging)
    implementation(Libraries.carbon)

    implementation(Libraries.Google.material)
    implementation(Libraries.Libs.slidingRootNav)
}

