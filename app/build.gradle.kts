import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.*

plugins {
    id("com.android.application")
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
    dataBinding {
        isEnabled = true
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.Libs.kotlin)
    implementation(Libraries.Libs.appcompat)
}

