package properties

import org.gradle.api.Project
import org.gradle.kotlin.dsl.listProperty
import kotlin.reflect.KProperty

internal class StringListProperty<T>(
    project: Project,
    default: List<String>
) {
    val property = project.objects.listProperty(String::class.java).apply {
        set(default)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): List<String> =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: List<String>) =
        this.property.set(value)
}