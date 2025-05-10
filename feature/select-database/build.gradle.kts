plugins {
    id("jero.kpass.android.feature")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.feature.select_database"
}

dependencies {
    implementation(libs.accompanist.systemuicontroller)
}