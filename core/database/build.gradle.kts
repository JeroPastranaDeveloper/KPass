plugins {
    id("jero.kpass.android.library")
    id("jero.kpass.android.koin")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jero.core.database"

    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(projects.core.model)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    // TODO
    /*implementation(projects.core.network)
    implementation(projects.core.domain)*/
    ksp(libs.androidx.room.compiler)

    // json parsing
    implementation(libs.kotlinx.serialization.json)
}