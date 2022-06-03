import common.GradleUtil.implement

plugins {
    id(app.Plugins.ANDROID_LIBRARY)
    id(app.Plugins.KOTLIN_ANDROID)
    id(app.Plugins.KOTLIN_KAPT)
    id(app.Plugins.HILT_ANDROID)
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

        buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
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

    app.ModuleDependencies.androidCore.implement(this)
    app.ModuleDependencies.coroutines.implement(this)
    app.ModuleDependencies.retrofit.implement(this)
    app.ModuleDependencies.okhttp.implement(this)
    app.ModuleDependencies.hilt.implement(this)
    app.ModuleDependencies.timber.implement(this)
}