package meugeninua.citiesnavigator.model.entities

import android.util.JsonReader
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.delegates.NamedMapDelegate
import meugeninua.citiesnavigator.model.readEntity

/**
 * @author meugen
 */
const val CITY_ENTITY = "city_entity"

class CityEntity(val map: MutableMap<String, Any>) {

    // Huge memory overhead +4 additional objects per EACH entity
    var country: String by NamedMapDelegate(map)
    var name: String by NamedMapDelegate(map)
    var lat: Double by NamedMapDelegate(map)
    var lng: Double by NamedMapDelegate(map)

    constructor(): this(mutableMapOf())

    constructor(
            country: String, name: String,
            lat: Double, lng: Double): this(mutableMapOf()) {
        this.country = country
        this.name = name
        this.lat = lat
        this.lng = lng
    }
}

class CityReader: EntityReader<CityEntity> {

    override fun nextEntity(reader: JsonReader): CityEntity {
        val entity = CityEntity()
        reader.readEntity {
            name ->

            when (name) {
                "country" -> entity.country = nextString()
                "name" -> entity.name = nextString()
                "lat" -> entity.lat = nextDouble()
                "lng" -> entity.lng = nextDouble()
                else -> skipValue()
            }
        }
        return entity
    }
}