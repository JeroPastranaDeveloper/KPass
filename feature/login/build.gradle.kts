plugins {
    id("jero.kpass.android.feature")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.feature.login"
}

dependencies {
    implementation(libs.accompanist.systemuicontroller)

    /*implementation(projects.utils)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.viewmodel)

    implementation(projects.feature.details)*/
}
