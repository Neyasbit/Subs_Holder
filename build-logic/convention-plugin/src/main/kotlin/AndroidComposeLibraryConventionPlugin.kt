import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import helpers.NamesOfPlugin.ANDROID_APPLICATION
import helpers.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidComposeLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.apply(ANDROID_APPLICATION)
        val exception = extensions.getByType<LibraryExtension>()
        configureAndroidCompose(exception)
    }
}