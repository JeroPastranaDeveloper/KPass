plugins {
    id("jero.kpass.android.library")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jero.core.model"
}

dependencies {
    // compose stable marker
    compileOnly(libs.compose.stable.marker)

    // kotlinx
    api(libs.kotlinx.immutable.collection)

    // Kotlin Serialization for Json
    implementation(libs.kotlinx.serialization.json)
}