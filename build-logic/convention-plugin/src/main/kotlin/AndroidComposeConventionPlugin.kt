import com.android.build.api.dsl.ApplicationExtension
import helpers.NamesOfPlugin.ANDROID_APPLICATION
import helpers.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * see build.gradle.kts gradlePlugin block
 */
@Suppress("unused")
internal class AndroidComposeConventionPlugin : Plugin<Project> {

    companion object {
        val classTag = AndroidComposeConventionPlugin::class.java.simpleName
    }

    override fun apply(target: Project) = target.run {
        pluginManager.apply(ANDROID_APPLICATION)
        val exception = extensions.getByType<ApplicationExtension>()
        configureAndroidCompose(exception)
    }
}