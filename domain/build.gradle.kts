import common.GradleUtil.implement

plugins {
    `java-library`
    id(app.Plugins.KOTLIN)
    id(app.Plugins.KOTLIN_KAPT)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    app.ModuleDependencies.javaInject.implement(this)
}
