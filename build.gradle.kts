import nl.littlerobots.vcu.plugin.versionCatalogUpdate

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.spotless.plugin)
        classpath(libs.versionCatalogUpdatePlugin)
    }
}

plugins {
    // This is what the plugin requires
    alias(libs.plugins.versionCatalogUpdatePlugin)
}

versionCatalogUpdate {
    sortByKey.set(false)
    keep {
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}