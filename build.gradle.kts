buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(ProjectDependencies.androidBuildTool)
        classpath(ProjectDependencies.kotlinGradlePlugin)
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