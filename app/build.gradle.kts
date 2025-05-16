plugins {
    id("jero.kpass.android.application")
    id("jero.kpass.android.application.compose")
    id("jero.kpass.android.koin")
}

android {
    namespace = "com.jero.kpass"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jero.kpass"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-Xno-param-assertions",
            "-Xno-call-assertions",
            "-Xno-receiver-assertions"
        )
        jvmTarget = "11"
    }
}

dependencies {
    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.material3.android)

    // Core
    implementation(projects.core.navigation)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.viewmodel)

    // Feature
    implementation(projects.feature.login)
    implementation(projects.feature.selectDatabase)
    implementation(projects.feature.home)
    implementation(projects.feature.addEditAccount)
    implementation(projects.feature.accountDetail)
}