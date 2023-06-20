package helpers

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.utils.named
import java.util.Locale

@Suppress("unused")
class GradleVersionsPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {

        rootProject.plugins.apply("com.github.ben-manes.versions")

        rootProject.tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {

            checkForGradleUpdate = true
            outputFormatter = "json"
            outputDir = "build/dependencyUpdates"
            reportfileName = "report"

            rejectVersionIf {
                isNonStable(candidate.version)
            }
        }
    }

    private fun isNonStable(version: String): Boolean {
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
            version.uppercase(Locale.getDefault()).contains(it)
        }

        val isStable = stableKeyword or regex.matches(version)

        return isStable.not()
    }
}