package properties

import org.gradle.api.Project
import kotlin.reflect.KProperty

internal class StringProperty<T>(
    project: Project,
    default: String
) {
    val property = project.objects.property(String::class.java).apply {
        set(default)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): String =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: String) =
        this.property.set(value)
}