plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Domain 모듈은 순수 Kotlin 코드만 사용
}
