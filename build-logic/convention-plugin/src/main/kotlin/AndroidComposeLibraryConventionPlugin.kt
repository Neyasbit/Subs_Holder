import com.android.build.gradle.LibraryExtension
import helpers.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * see build.gradle.kts gradlePlugin block
 */
@Suppress("unused")
internal class AndroidComposeLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.apply("com.android.library")
        val exception = extensions.getByType<LibraryExtension>()
        configureAndroidCompose(exception)
    }
}