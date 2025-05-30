plugins {
    id("jero.kpass.android.library")
    id("jero.kpass.android.koin")
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.jero.core.data"
    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    // core modules
    api(projects.core.model)
    // implementation(projects.core.network)
    implementation(projects.core.domain)
    implementation(projects.core.utils)
    // implementation(projects.core.viewmodel)
    // testImplementation(projects.core.test)

    // kotlinx
    api(libs.kotlinx.immutable.collection)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // Kotlin Serialization for Json
    implementation(libs.kotlinx.serialization.json)

    // network
    implementation(libs.sandwich)
    /*testImplementation(projects.core.test)
    testImplementation(projects.feature.home)
    testImplementation(projects.utils)
    testImplementation(projects.feature.details)*/

    implementation(libs.gson)

    // unit test
    /*testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)*/
}