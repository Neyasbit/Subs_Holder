import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode
import java.io.File
import java.io.FileNotFoundException
import java.util.Stack
import kotlin.jvm.Throws


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
        val gradleDirectoriesFilter = FilterProjectDirectory.DirectoryWithBuildGradle()
        val includeDirProcess = IncludeDirProcess.IncludeDirectories(rootDir.name)
        val directoriesManager = FileDirectoriesProcess.DirectoryScanner(
            gradleDirectoriesFilter,
            includeDirProcess
        )
        directoriesManager.findDirectoriesWithBuildGradle(rootDir)
        includeDirProcess.includeDirectories.forEach(::include)
    }
}

private interface FilterProjectDirectory {

    companion object {
        private const val BUILD_GRADLE_FILE_NAME_KTS = "/build.gradle.kts"
        private const val BUILD_GRADLE_FILE_NAME = "/build.gradle"

        val isDirectoryNotHiddenAndNoBuild: (File) -> Boolean = { file ->
            !file.isHidden && file.isDirectory && NoBuildDirectory().directoryFilter(file)
        }
    }

    @Throws(FileNotFoundException::class)
    fun directoryFilter(file: File): Boolean

    class DirectoryWithBuildGradle : FilterProjectDirectory {
        @Throws(FileNotFoundException::class)
        override fun directoryFilter(file: File): Boolean =
            file.resolve(file.path + BUILD_GRADLE_FILE_NAME_KTS)
                .exists() || file.resolve(file.path + BUILD_GRADLE_FILE_NAME).exists()
    }

    private class NoBuildDirectory : FilterProjectDirectory {
        override fun directoryFilter(file: File): Boolean = !file.name.startsWith("build")
    }
}

private interface FileDirectoriesProcess {
    @Throws(FileNotFoundException::class)
    fun findDirectoriesWithBuildGradle(rootDir: File): List<File>

    class DirectoryScanner(
        private val filter: FilterProjectDirectory,
        private val includeDirProcess: IncludeDirProcess
    ) :
        FileDirectoriesProcess {

        override fun findDirectoriesWithBuildGradle(rootDir: File): List<File> {

            val gradleDirectories = mutableListOf<File>()
            val directories = Stack<File>()

            directories.push(rootDir)

            while (directories.isNotEmpty()) {
                val currentDir = directories.pop()
                currentDir.listFiles(FilterProjectDirectory.isDirectoryNotHiddenAndNoBuild)
                    ?.forEach { subdir ->
                        if (filter.directoryFilter(subdir)) {
                            gradleDirectories.add(subdir)
                            includeDirProcess.formattingDirName(subdir)
                        }
                        directories.push(subdir)
                    }
            }
            return gradleDirectories
        }
    }
}

private interface IncludeDirProcess {

    val includeDirectories: Set<String>
    fun formattingDirName(gradleDir: File)

    class IncludeDirectories(
        private val rootDirName: String
    ) : IncludeDirProcess {

        private val directories = mutableSetOf<String>()
        override val includeDirectories: Set<String> = directories
        override fun formattingDirName(gradleDir: File) {
            val includeName = gradleDir.path
                .substringAfter(rootDirName)
                .replace("/", ":")
            directories.add(includeName)
        }
    }
}
