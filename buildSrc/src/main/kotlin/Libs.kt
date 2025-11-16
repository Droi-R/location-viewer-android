object Libs {
    // Core
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"

    // Lifecycle
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

    // Coroutines
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // AndroidX Hilt (Work)
    const val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltX}"
    const val hiltXCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltX}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // WorkManager
    const val workRuntimeKtx = "androidx.work:work-runtime-ktx:${Versions.work}"

    // NAVER Map
    const val naverMapSdk = "com.naver.maps:map-sdk:${Versions.naverMap}"

    // Play Services Location
    const val playServicesLocation =
        "com.google.android.gms:play-services-location:${Versions.playServicesLocation}"

    // Test
    const val junit = "junit:junit:${Versions.junit}"
    const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

}