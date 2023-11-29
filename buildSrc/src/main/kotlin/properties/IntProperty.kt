package properties

import org.gradle.api.Project
import kotlin.reflect.KProperty

internal class IntProperty<T>(
    project: Project,
    default: Int
) {
    val property = project.objects.property(Int::class.java).apply {
        set(default)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): Int =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: Int) =
        this.property.set(value)
}