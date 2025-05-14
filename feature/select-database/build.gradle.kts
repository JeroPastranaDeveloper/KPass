plugins {
    id("jero.kpass.android.feature")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.feature.select_database"
}

dependencies {
    implementation(libs.accompanist.systemuicontroller)

    implementation(projects.core.viewmodel)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.screen)
    implementation(libs.firebase.crashlytics.buildtools)
}
