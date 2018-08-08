package meugeninua.citiesnavigator.model.entities

import android.content.ContentValues
import android.database.Cursor
import android.util.JsonReader
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.readEntity

/**
 * @author meugen
 */
const val CITY_ENTITY = "city_entity"

private const val FLD_ID = "id"
private const val FLD_COUNTRY = "country"
private const val FLD_NAME = "name"
private const val FLD_LAT = "lat"
private const val FLD_LNG = "lng"

data class CityEntity(
        var id: Int,
        var country: String,
        var name: String,
        var lat: Double,
        var lng: Double) {

    constructor(): this(0, "", "", 0.0, 0.0)

    constructor(cursor: Cursor): this(
            cursor.getInt(FLD_ID),
            cursor.getString(FLD_COUNTRY),
            cursor.getString(FLD_NAME),
            cursor.getDouble(FLD_LAT),
            cursor.getDouble(FLD_LNG))
}

fun CityEntity.toContentValues(): ContentValues {
    val result = ContentValues()
    result.put(FLD_COUNTRY, country)
    result.put(FLD_NAME, name)
    result.put(FLD_LAT, lat)
    result.put(FLD_LNG, lng)
    return result
}

class CityReader: EntityReader<CityEntity> {

    override fun nextEntity(reader: JsonReader): CityEntity {
        val entity = CityEntity()
        reader.readEntity {
            when (it) {
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