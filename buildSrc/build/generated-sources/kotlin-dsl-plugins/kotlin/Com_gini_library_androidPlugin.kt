/**
 * Precompiled [com.gini.library.android.gradle.kts][Com_gini_library_android_gradle] script plugin.
 *
 * @see Com_gini_library_android_gradle
 */
public
class Com_gini_library_androidPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Com_gini_library_android_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
