// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:3.6.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        classpath ("com.google.gms:google-services:4.3.3")
        classpath("com.android.tools.build:gradle:${Libraries.Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Libraries.Versions.kotlin}")
        classpath(Libraries.Google.gmsGoogleServices)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}