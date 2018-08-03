package meugeninua.citiesnavigator.model.entities

import android.util.JsonReader
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.delegates.NamedMapDelegate
import meugeninua.citiesnavigator.model.readEntity

/**
 * @author meugen
 */
const val COUNTRY_ENTITY = "country_entity"

class CountryEntity(val map: MutableMap<String, Any>) {

    // Huge memory overhead +4 additional objects per EACH entity
    var name: String by NamedMapDelegate(map)
    var code: String by NamedMapDelegate(map)
    var group: String by NamedMapDelegate(map, "country_group")
    var states: String by NamedMapDelegate(map)

    constructor(): this(mutableMapOf())

    constructor(
            name: String, code: String,
            group: String, states: String): this(mutableMapOf()) {
        this.name = name
        this.code = code
        this.group = group
        this.states = states
    }
}

class CountryReader: EntityReader<CountryEntity> {

    override fun nextEntity(reader: JsonReader): CountryEntity {
        val entity = CountryEntity()
        reader.readEntity {
            name ->

            when (name) {
                "name" -> entity.name = nextString()
                "code" -> entity.code = nextString()
                "group" -> entity.group = nextString()
                "states" -> entity.states = nextString()
                else -> skipValue()
            }
        }
        return entity
    }
}