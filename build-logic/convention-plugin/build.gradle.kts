plugins {
    `kotlin-dsl`
}

group = "com.example.subholder.buildlogic"

// This repositories are required to connect non-official
repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    compileOnly(libs.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)

    implementation(libs.detekt.plugin)
    implementation(libs.ktlint.jlleitschuh.plugin)
    implementation(libs.spotless.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "subholder.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "subholder.android.application.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "subholder.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "subholder.android.compose.library"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }
        register("androidAppQualityPlugin") {
            id = "subholder.android.codequality"
            implementationClass = "CodeQualityConventionPlugin"
        }
    }
}