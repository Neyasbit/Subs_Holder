plugins {
    `kotlin-dsl`
    //asd
}

group = "com.example.subholder.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)

     implementation(libs.detekt.plugin)
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