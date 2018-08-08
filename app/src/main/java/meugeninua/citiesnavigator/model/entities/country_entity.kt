package meugeninua.citiesnavigator.model.entities

import android.content.ContentValues
import android.database.Cursor
import android.util.JsonReader
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.readEntity

/**
 * @author meugen
 */
const val COUNTRY_ENTITY = "country_entity"

private const val FLD_ID = "id"
private const val FLD_NAME = "name"
private const val FLD_CODE = "code"
private const val FLD_GROUP = "country_group"
private const val FLD_STATES = "states"

class CountryEntity(
        var id: Int,
        var name: String,
        var code: String,
        var group: String,
        var states: String) {

    constructor(): this(0, "", "", "", "")

    constructor(cursor: Cursor): this(
            cursor.getInt(FLD_ID),
            cursor.getString(FLD_NAME),
            cursor.getString(FLD_CODE),
            cursor.getString(FLD_GROUP),
            cursor.getString(FLD_STATES))
}

fun CountryEntity.toContentValues(): ContentValues {
    val result = ContentValues()
    result.put(FLD_ID, id)
    result.put(FLD_NAME, name)
    result.put(FLD_CODE, code)
    result.put(FLD_GROUP, group)
    result.put(FLD_STATES, states)
    return result
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