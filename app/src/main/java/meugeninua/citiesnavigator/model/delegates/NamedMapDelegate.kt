package meugeninua.citiesnavigator.model.delegates

import kotlin.reflect.KProperty

/**
 * @author meugen
 */
class NamedMapDelegate<T>(
        private val map: MutableMap<String, Any>,
        private val name: String? = null) {

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return map[name ?: property.name] as T
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        map[name ?: property.name] = value as Any
    }
}