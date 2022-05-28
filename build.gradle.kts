buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(ProjectDependencies.ANDROID_BUILD_TOOL)
        classpath(ProjectDependencies.KOTLIN_GRADLE_PLUGIN)
        classpath(ProjectDependencies.HILT_GRADLE_PLUGIN)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}