import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode

@Suppress("UnstableApiUsage", "unused")
internal class SettingsConventionPlugin : Plugin<Settings> {

    private val codeQualityDirName = "codequality"
    private val buildDirName = "build"

    override fun apply(target: Settings) = target.run {

        dependencyResolutionManagement {
            repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            repositories {
                google()
                mavenCentral()
            }
        }
        val filterProjectDirectory = rootDir
            .listFiles()
            ?.filter {
                it.isDirectory
                        && !it.isHidden &&
                        it.name != codeQualityDirName &&
                        !it.name.startsWith(buildDirName)
            }
            ?.forEach { file -> include(file.name) }
    }
}