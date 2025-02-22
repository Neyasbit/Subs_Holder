

plugins {
    // this plugins registered in build.gradle "convention-plugin" module
    id("subholder.gradleVersionPlugin")
    id("subholder.android.application")
    id("subholder.android.application.compose")
    id("subholder.android.codequality")
}

android {
    defaultConfig {
        applicationId = "com.example.subsholder"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    namespace = "com.subsholder"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.material)
    implementation(libs.junit4)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.test.core)
}
