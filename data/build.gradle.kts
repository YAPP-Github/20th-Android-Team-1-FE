import common.GradleUtil.implement

plugins {
    id(app.Plugins.androidLibrary)
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))

    app.AppDependencies.androidCoreDependencies.implement(this)
    app.AppDependencies.coroutineDependencies.implement(this)
    app.AppDependencies.retrofitDependencies.implement(this)
    app.AppDependencies.okhttpDependencies.implement(this)
}