package app

import Versions
import common.DependencyInfo
import common.Method

object ModuleDependencies {
    val androidCore = arrayOf(
        DependencyInfo(
            "org.jetbrains.kotlin:kotlin-stdlib",
            Versions.KOTLIN,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.core:core-ktx",
            Versions.ANDROID_CORE,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.appcompat:appcompat",
            Versions.ANDROID_APP_COMPAT,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.constraintlayout:constraintlayout",
            Versions.CONSTRAINT_LAYOUT,
            Method.IMPLEMENTATION
        ),
        DependencyInfo("junit:junit", Versions.JUNIT, Method.TEST_IMPLEMENTATION),
        DependencyInfo(
            "androidx.lifecycle:lifecycle-runtime-ktx",
            Versions.LIFECYCLE,
            Method.IMPLEMENTATION
        ),
    )

    val compose = arrayOf(
        DependencyInfo("androidx.compose.ui:ui", Versions.COMPOSE, Method.IMPLEMENTATION),
        DependencyInfo(
            "androidx.compose.material:material",
            Versions.COMPOSE,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.compose.ui:ui-tooling-preview",
            Versions.COMPOSE,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.compose.ui:ui-tooling",
            Versions.COMPOSE,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.compose.ui:ui-tooling",
            Versions.COMPOSE,
            Method.DEBUG_IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.compose.material:material-icons-extended",
            Versions.COMPOSE,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.activity:activity-compose",
            Versions.COMPOSE_ACTIVITY,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.constraintlayout:constraintlayout-compose",
            Versions.COMPOSE_CONSTRAINT_LAYOUT,
            Method.IMPLEMENTATION
        ),
        DependencyInfo("io.coil-kt:coil-compose", Versions.COIL, Method.IMPLEMENTATION),
        DependencyInfo(
            "androidx.navigation:navigation-compose",
            Versions.COMPOSE_NAVIGATION,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "androidx.constraintlayout:constraintlayout-compose",
            Versions.COMPOSE_CONSTRAINT_LAYOUT,
            Method.IMPLEMENTATION
        )
    )

    val coroutines = arrayOf(
        DependencyInfo(
            "org.jetbrains.kotlinx:kotlinx-coroutines-core",
            Versions.KOTLIN_COROUTINES,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "org.jetbrains.kotlinx:kotlinx-coroutines-android",
            Versions.KOTLIN_COROUTINES,
            Method.IMPLEMENTATION
        )
    )

    val retrofit = arrayOf(
        DependencyInfo(
            "com.squareup.retrofit2:retrofit", Versions.RETROFIT, Method.IMPLEMENTATION),
        DependencyInfo(
            "com.squareup.retrofit2:converter-moshi",
            Versions.RETROFIT,
            Method.IMPLEMENTATION
        ),
        DependencyInfo("com.squareup.moshi:moshi-kotlin",
            Versions.MOSHI,
            Method.IMPLEMENTATION)
    )

    val okhttp = arrayOf(
        DependencyInfo("com.squareup.okhttp3:okhttp", Versions.OKHTTP, Method.IMPLEMENTATION),
        DependencyInfo(
            "com.squareup.okhttp3:logging-interceptor",
            Versions.OKHTTP,
            Method.IMPLEMENTATION
        )
    )

    val hilt = arrayOf(
        DependencyInfo(
            "com.google.dagger:hilt-android",
            Versions.HILT,
            Method.IMPLEMENTATION
        ),
        DependencyInfo(
            "com.google.dagger:hilt-compiler",
            Versions.HILT,
            Method.KAPT
        ),
    )

    val hiltAndroid = arrayOf(
        DependencyInfo("androidx.hilt:hilt-compiler", Versions.HILT_ANDROID, Method.KAPT),
        DependencyInfo(
            "androidx.hilt:hilt-navigation-compose",
            Versions.HILT_ANDROID,
            Method.IMPLEMENTATION
        ),
    )

    val timber = arrayOf(
        DependencyInfo("com.jakewharton.timber:timber", Versions.TIMBER, Method.IMPLEMENTATION)
    )

    val javaInject = arrayOf(
        DependencyInfo("javax.inject:javax.inject", Versions.INJECT, Method.IMPLEMENTATION)
    )

    val materialCalendarView = arrayOf(
        DependencyInfo("com.prolificinteractive:material-calendarview",
            Versions.MATERIAL_CALENDAR,
            Method.IMPLEMENTATION)
    )

    val kakaoOAuth =
        arrayOf(DependencyInfo("com.kakao.sdk:v2-user", Versions.KAKAO_SDK, Method.IMPLEMENTATION))
    val kakaoShare =
        arrayOf(DependencyInfo("com.kakao.sdk:v2-share", Versions.KAKAO_SDK, Method.IMPLEMENTATION))

    val accompanist = arrayOf(
        DependencyInfo("com.google.accompanist:accompanist-pager",
            Versions.ACCOMPANIST_PAGER,
            Method.IMPLEMENTATION),

        DependencyInfo("com.google.accompanist:accompanist-systemuicontroller",
            Versions.ACCOMPANIST_SYSTEM_UI_CONTROLLER,
            Method.IMPLEMENTATION)
    )

    val kotlinDateTime = arrayOf(
        DependencyInfo("org.jetbrains.kotlinx:kotlinx-datetime",
            Versions.KOTLIN_DATETIMES,
            Method.IMPLEMENTATION)
    )

    val shimmer = arrayOf(
        DependencyInfo("com.valentinilk.shimmer:compose-shimmer",
            Versions.SHIMMER,
            Method.IMPLEMENTATION
        )
    )

    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
    const val FIREBASE_DYNAMICLINKS = "com.google.firebase:firebase-dynamic-links-ktx"
}
