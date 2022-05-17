package app

import common.DependencyInfo
import common.Method

object AppDependencies {
    val androidCoreDependencies = arrayOf(
        DependencyInfo("org.jetbrains.kotlin:kotlin-stdlib", Versions.kotlinVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.core:core-ktx", Versions.androidCoreVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.appcompat:appcompat", Versions.androidAppCompatVersion, Method.IMPLEMENTATION),
        DependencyInfo("androidx.constraintlayout:constraintlayout", Versions.constraintlayoutVersion, Method.IMPLEMENTATION),
        DependencyInfo("junit:junit", Versions.androidAppCompatVersion, Method.TESTIMPLEMENTATION)
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
}
