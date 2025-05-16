plugins {
    id("jero.kpass.android.feature")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.biometric_authentication"
}

dependencies {
    implementation(libs.androidx.biometric)
}
