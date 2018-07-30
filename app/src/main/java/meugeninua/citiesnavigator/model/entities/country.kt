package meugeninua.citiesnavigator.model.entities

import android.util.JsonReader
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.readEntity

/**
 * @author meugen
 */
class CountryEntity(val map: MutableMap<String, Any>) {

    var name: String by map
    var code: String by map
    var group: String by map
    var states: String by map

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

const val COUNTRY_READER = "country_reader"

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