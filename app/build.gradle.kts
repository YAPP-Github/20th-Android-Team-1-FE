import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import common.GradleUtil.implement

plugins {
    id(app.Plugins.ANDROID_APPLICATION)
    id(app.Plugins.KOTLIN_ANDROID)
    id(app.Plugins.KOTLIN_KAPT)
    id(app.Plugins.GOOGLE_SERVICE)
    id(app.Plugins.FIREBASE_CRASHLYTICS)
    id(app.Plugins.HILT_ANDROID)
    id(app.Plugins.SECRETS_GRADLE)
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        applicationId = Configs.APPLICATION_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = Configs.VERSION_CODE
        versionName = Configs.VERSION_NAME

        getProperty("KAKAO_APP_KEY")?.let { resValue("string", "kakao_sdk_app_key", it) }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("$rootDir/debug.keystore")
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

fun getProperty(propertyKey: String): String? {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation(project(Modules.PRESENTATION))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))

    app.ModuleDependencies.hilt.implement(this)
    app.ModuleDependencies.hiltAndroid.implement(this)
    app.ModuleDependencies.timber.implement(this)
    app.ModuleDependencies.kakaoSdk.implement(this)

    implementation(platform(app.ModuleDependencies.FIREBASE_BOM))
    implementation(app.ModuleDependencies.FIREBASE_ANALYTICS)
    implementation(app.ModuleDependencies.FIREBASE_CRASHLYTICS)
}
