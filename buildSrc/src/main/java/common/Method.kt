package common

enum class Method(val methodString : String) {
    IMPLEMENTATION("implementation"),
    TEST_IMPLEMENTATION("testImplementation"),
    KAPT("kapt"),
    ANDROID_TEST_IMPLEMENTATION("androidTestImplementation"),
    DEBUG_IMPLEMENTATION("debugImplementation")
}