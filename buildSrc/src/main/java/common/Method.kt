package common

enum class Method(val methodString : String) {
    IMPLEMENTATION("implementation"),
    TESTIMPLEMENTATION("testImplementation"),
    KAPT("kapt"),
    ANDROIDTESTIMPLEMENTATION("androidTestImplementation")
}