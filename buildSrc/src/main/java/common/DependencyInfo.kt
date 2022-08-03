package common

import org.gradle.api.artifacts.dsl.DependencyHandler

data class DependencyInfo(val name: String, val version: String, val method: Method) {
    private fun getFullName() = "$name:$version"
    private fun getMethodName() = method.methodString

    fun implement(dependencyHandler: DependencyHandler) {
        dependencyHandler.add(this.getMethodName(), this.getFullName())
    }
}