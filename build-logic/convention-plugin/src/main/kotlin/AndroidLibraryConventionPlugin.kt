import com.android.build.gradle.LibraryExtension
import helpers.NamesOfPlugin.ANDROID_APPLICATION
import helpers.NamesOfPlugin.ANDROID_KOTLIN
import helpers.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * see build.gradle.kts gradlePlugin block
 */
@Suppress("unused")
internal class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        with(pluginManager) {
            apply(ANDROID_APPLICATION)
            apply(ANDROID_KOTLIN)
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = 33
        }
    }
}