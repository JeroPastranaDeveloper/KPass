plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("jero.kpass.android.library")
    id("jero.kpass.android.library.compose")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.core.navigation"
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.kotlinx.coroutines.android)

    api(libs.androidx.navigation.compose)

    // json parsing
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.model)
    implementation(libs.gson)
}