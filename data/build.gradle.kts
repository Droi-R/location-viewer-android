plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android") version Versions.hilt
    kotlin("kapt")
}

android {
    namespace = "com.droker.realtimelocationmap"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Libs.coreKtx)

    // Room
    implementation(Libs.roomRuntime)
    implementation(Libs.roomKtx)
    kapt(Libs.roomCompiler)

    // Hilt
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidJunit)
    androidTestImplementation(Libs.espresso)
}
