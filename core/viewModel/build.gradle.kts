plugins {
    id("jero.kpass.android.library")
}

android {
    namespace = "com.jero.core.viewmodel"
}

dependencies {
    api(libs.androidx.lifecycle.viewModelCompose)
}
