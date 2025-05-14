plugins {
    id("jero.kpass.android.library")
    id("jero.kpass.android.feature")
}

android {
    namespace = "com.jero.core.screen"
}

dependencies {
    implementation(libs.accompanist.systemuicontroller)
}
