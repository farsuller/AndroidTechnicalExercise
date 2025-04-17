// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.devtool.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.spotless) apply false
}

buildscript {
    dependencies {
        classpath(libs.spotless)
    }
}


subprojects {
    afterEvaluate {
        project.apply("${project.rootDir}/spotless.gradle")
    }
}