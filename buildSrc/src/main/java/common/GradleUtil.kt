package common

import org.gradle.api.artifacts.dsl.DependencyHandler

object GradleUtil {
    fun Array<Array<DependencyInfo>>.implement(dependencyHandler: DependencyHandler) =
        this.forEach { it.forEach { dependency -> dependency.implement(dependencyHandler) } }

    fun Array<DependencyInfo>.implement(dependencyHandler: DependencyHandler) =
        this.forEach { dependency -> dependency.implement(dependencyHandler) }
}