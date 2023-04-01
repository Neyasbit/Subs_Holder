pluginManagement {
    // This options can rename a folder standard "buildSrc"
    // it also required to load custom settings plugins
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("subholder.settings")
}

rootProject.name = "Subs Holder"

