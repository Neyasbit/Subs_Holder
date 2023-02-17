package helpers

import com.android.build.gradle.internal.crash.afterEvaluate
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

internal class SpotlessAutoPreBuildTask : DefaultTask() {
    @TaskAction
    fun autoCheckAndApplySpotless() {
        val tasks = project.tasks
        afterEvaluate {
            tasks
                .getByName("spotlessCheck")
                .dependsOn(
                    tasks.getByName("spotlessApply")
                )
        }
    }
}