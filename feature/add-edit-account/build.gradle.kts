plugins {
    id("jero.kpass.android.feature")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.feature.addeditaccount"
}

dependencies {
    implementation(libs.accompanist.systemuicontroller)

    implementation(projects.core.viewmodel)
    implementation(projects.core.domain)
    implementation(projects.core.screen)
    implementation(projects.core.utils)
}
