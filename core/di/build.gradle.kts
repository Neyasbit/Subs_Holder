plugins {
    id("subholder.android.library")
    id("subholder.android.codequality")
    id("subholder.android.compose.library")
}

android {
    namespace = "com.core.di"
}
dependencies {
    implementation(libs.koin.core)
    api(libs.androidx.compose.runtime)
}
