import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


@Suppress("UnstableApiUsage", "unused")
internal class SettingsConventionPlugin : Plugin<Settings> {

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
            ?.filter { file -> file.isDirectory && !file.isHidden }
            ?.filter { file ->
                file
                    .listFiles()
                    ?.filter { subFile ->
                        FilterProjectDirContract.SubProjectFiler().directoryFilter(file, subFile)
                    }?.forEach { subFile ->
                        include(":${file.name}:${subFile.name}")
                    }
                FilterProjectDirContract.RootDir().directoryFilter(file)
            }
            ?.forEach { file -> include(file.name) }
    }
}

internal interface FilterProjectDirContract {


    val buildGradleFileName get() = "/build.gradle.kts"

    fun directoryFilter(file: File, subFile: File? = null): Boolean

    class RootDir : FilterProjectDirContract {
        override fun directoryFilter(file: File, subFile: File?) =
            Files.exists(Paths.get(file.path + buildGradleFileName))

    }

    class SubProjectFiler : FilterProjectDirContract {
        override fun directoryFilter(file: File, subFile: File?): Boolean {
            val buildDirName = "build"

            return !file.name.startsWith(buildDirName) &&
                    Files.exists(Paths.get(subFile?.path + buildGradleFileName))

        }
    }
}