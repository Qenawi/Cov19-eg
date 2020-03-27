import org.gradle.internal.impldep.org.bouncycastle.asn1.iana.IANAObjectIdentifiers.experimental
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
    dataBinding { isEnabled = true }
    androidExtensions { isExperimental=true }
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
    implementation(Libraries.Google.crashlytics)
    implementation(Libraries.Fragment.fragment)
    implementation(Libraries.Lifecycle.extensions)
    implementation(Libraries.Glide.library)
    kapt(Libraries.Glide.compiler)

}

