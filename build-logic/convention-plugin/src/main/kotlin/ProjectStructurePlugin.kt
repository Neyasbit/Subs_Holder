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
            .listFiles(FilterProjectDirectory.isDirectoryNotHidden)
            ?.flatMap { dir ->
                val subDirs = dir.listFiles(FilterProjectDirectory.isDirectoryNotHidden)?.toList()
                    ?: emptyList()
                listOf(dir).plus(subDirs)
            }
            ?.filter { subDir ->
                FilterProjectDirectory
                    .SubProjectFiler(subDir)
                    .directoryFilter(subDir.parentFile)
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

        val isDirectoryNotHidden: (File) -> Boolean = { file ->
            !file.isHidden && file.isDirectory
        }
    }

    fun directoryFilter(file: File): Boolean

    private class DirectoryWithBuildGradle : FilterProjectDirectory {
        override fun directoryFilter(file: File) =
            file.resolve(file.path + BUILD_GRADLE_FILE_NAME).exists()

    }

    class SubProjectFiler(private val subFile: File) : FilterProjectDirectory {
        override fun directoryFilter(file: File): Boolean {
            val buildDirName = "build"
            val gradleDir = DirectoryWithBuildGradle().directoryFilter(subFile)
            return !file.name.startsWith(buildDirName) && gradleDir
        }
    }
}