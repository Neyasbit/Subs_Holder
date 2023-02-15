import helpers.AppDetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class CodeQualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        apply<AppDetektPlugin>()
    }
}