import common.GradleUtil.implement

plugins {
    id(app.Plugins.androidLibrary)
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.yapp.presentation"
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }
}

dependencies {
    app.AppDependencies.androidCoreDependencies.implement(this)
    app.AppDependencies.composeDependencies.implement(this)
}