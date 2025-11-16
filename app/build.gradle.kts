plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android") version Versions.hilt
    kotlin("kapt")
}

android {
    namespace = "com.droker.realtimelocationmap"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.droker.realtimelocationmap"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Libs.coreKtx)
    implementation(Libs.appCompat)
    implementation(Libs.material)
    implementation(Libs.activityKtx)
    implementation(Libs.lifecycleRuntimeKtx)
    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.lifecycleLiveDataKtx)

    // NAVER Map
    implementation(Libs.naverMapSdk)

    // WorkManager
    implementation(Libs.workRuntimeKtx)

    // Play Services Location
    implementation(Libs.playServicesLocation)

    // Hilt
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)

    // Hilt + WorkManager
    implementation(Libs.hiltWork)
    kapt(Libs.hiltXCompiler)

    implementation(Libs.javapoet)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidJunit)
    androidTestImplementation(Libs.espresso)
}