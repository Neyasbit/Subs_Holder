import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode
import java.io.File


@Suppress("UnstableApiUsage", "unused")
internal class ProjectStructurePlugin : Plugin<Settings> {

    override fun apply(target: Settings) = target.run {

        dependencyResolutionManagement {
            repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            repositories {
                google()
                mavenCentral()
            }
        }

        rootDir
            .listFiles(FilterProjectDirectory.isDirectoryNotHiddenAndNoBuild)
            ?.flatMap { dir ->
                val subDirs =
                    dir
                        .listFiles(FilterProjectDirectory.isDirectoryNotHiddenAndNoBuild)
                        ?.toList()
                        ?: emptyList()
                listOf(dir).plus(subDirs)
            }
            ?.filter { subDir ->
                FilterProjectDirectory
                    .DirectoryWithBuildGradle()
                    .directoryFilter(subDir)
            }
            ?.mapNotNull { dir ->
                val projectName = dir.parentFile.name
                    .takeIf { projectName -> projectName != rootDir.name }
                    ?.let { ":$it" }
                    .orEmpty()
                "$projectName:${dir.name}"
            }
            ?.toSet()
            ?.forEach(::include)

        Unit
    }
}

private interface FilterProjectDirectory {

    companion object {
        private const val BUILD_GRADLE_FILE_NAME = "/build.gradle.kts"

        val isDirectoryNotHiddenAndNoBuild: (File) -> Boolean = { file ->
            !file.isHidden && file.isDirectory && NoBuildDirectory().directoryFilter(file)
        }
    }

    fun directoryFilter(file: File): Boolean

    class DirectoryWithBuildGradle : FilterProjectDirectory {
        override fun directoryFilter(file: File): Boolean =
            file.resolve(file.path + BUILD_GRADLE_FILE_NAME).exists()
    }

    private class NoBuildDirectory : FilterProjectDirectory {
        override fun directoryFilter(file: File): Boolean =
            !file.name.startsWith("build")
    }
}