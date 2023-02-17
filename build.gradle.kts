buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.spotless.plugin)
    }
}

plugins {
    //this plugins can be managed in lib.versions.toml
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
}