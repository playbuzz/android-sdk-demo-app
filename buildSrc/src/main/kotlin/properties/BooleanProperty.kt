package properties

import org.gradle.api.Project
import kotlin.reflect.KProperty

internal class BooleanProperty<T>(
    project: Project,
    default: Boolean
) {
    val property = project.objects.property(Boolean::class.java).apply {
        set(default)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): Boolean =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: Boolean) =
        this.property.set(value)
}