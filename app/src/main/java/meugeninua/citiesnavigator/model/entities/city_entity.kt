package meugeninua.citiesnavigator.model.entities

import android.util.JsonReader
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.readEntity

/**
 * @author meugen
 */
const val CITY_ENTITY = "city_entity"

class CityEntity(val map: MutableMap<String, Any>) {

    var country: String by map
    var name: String by map
    var lat: Double by map
    var lng: Double by map

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