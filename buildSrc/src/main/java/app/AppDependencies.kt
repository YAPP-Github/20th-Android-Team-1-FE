package app

import common.DependencyInfo
import common.Method

object AppDependencies {
    val androidCoreDependencies = arrayOf(
        DependencyInfo("org.jetbrains.kotlin:kotlin-stdlib", Versions.kotlinVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.core:core-ktx", Versions.androidCoreVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.appcompat:appcompat", Versions.androidAppCompatVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.constraintlayout:constraintlayout", Versions.constraintlayoutVersion, Method.IMPLEMENTATION),
        DependencyInfo("junit:junit", Versions.junitVersion, Method.TESTIMPLEMENTATION)
    )

    val composeDependencies = arrayOf(
        DependencyInfo("androidx.lifecycle:lifecycle-runtime-ktx", Versions.lifecycleVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.compose.ui:ui", Versions.composeVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.compose.material:material", Versions.composeVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.compose.ui:ui-tooling-preview", Versions.composeVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.compose.ui:ui-tooling", Versions.composeVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.compose.material:material-icons-extended", Versions.composeVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.activity:activity-compose", Versions.activityComposeVersion, Method.IMPLEMENTATION),
        DependencyInfo("io.coil-kt:coil-compose", Versions.coilVersion, Method.IMPLEMENTATION)
    )

    val coroutineDependencies = arrayOf(
        DependencyInfo("org.jetbrains.kotlinx:kotlinx-coroutines-core", Versions.kotlinCoroutineVersion, Method.IMPLEMENTATION),
        DependencyInfo("org.jetbrains.kotlinx:kotlinx-coroutines-android", Versions.kotlinCoroutineVersion, Method.IMPLEMENTATION)
    )

    val retrofitDependencies = arrayOf(
        DependencyInfo("com.squareup.retrofit2:retrofit", Versions.retrofitVersion, Method.IMPLEMENTATION),
        DependencyInfo("com.squareup.retrofit2:converter-gson", Versions.retrofitVersion, Method.IMPLEMENTATION)
    )

    val okhttpDependencies = arrayOf(
        DependencyInfo("com.squareup.okhttp3:okhttp", Versions.okhttpVersion, Method.IMPLEMENTATION),
        DependencyInfo("com.squareup.okhttp3:logging-interceptor", Versions.okhttpVersion, Method.IMPLEMENTATION)
    )
}
