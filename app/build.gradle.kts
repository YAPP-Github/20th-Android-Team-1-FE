import common.GradleUtil.implement

plugins {
    id(app.Plugins.ANDROID_APPLICATION)
    id(app.Plugins.KOTLIN_ANDROID)
    id(app.Plugins.KOTLIN_KAPT)
    id(app.Plugins.GOOGLE_SERVICE)
    id(app.Plugins.FIREBASE_CRASHLYTICS)
    id(app.Plugins.HILT_ANDROID)
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        applicationId = Configs.APPLICATION_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = Configs.VERSION_CODE
        versionName = Configs.VERSION_NAME
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Modules.PRESENTATION))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))

    app.ModuleDependencies.hilt.implement(this)
    app.ModuleDependencies.hiltAndroid.implement(this)
    app.ModuleDependencies.timber.implement(this)
    
    implementation(platform(app.ModuleDependencies.FIREBASE_BOM))
    implementation(app.ModuleDependencies.FIREBASE_ANALYTICS)
    implementation(app.ModuleDependencies.FIREBASE_CRASHLYTICS)
}