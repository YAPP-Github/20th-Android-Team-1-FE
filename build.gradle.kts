buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(ProjectDependencies.ANDROID_BUILD_TOOL)
        classpath(ProjectDependencies.KOTLIN_GRADLE_PLUGIN)
        classpath(ProjectDependencies.HILT_GRADLE_PLUGIN)
        classpath(ProjectDependencies.FIREBASE_CRASHLYTICS)
        classpath(ProjectDependencies.GOOGLE_SERVICE)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}