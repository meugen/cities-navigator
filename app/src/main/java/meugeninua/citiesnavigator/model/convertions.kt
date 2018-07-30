package meugeninua.citiesnavigator.model

import android.content.ContentValues
import android.database.Cursor
import android.util.JsonReader

/**
 * @author meugen
 */
interface EntityReader<T> {

    fun nextEntity(reader: JsonReader): T
}

fun <T> EntityReader<T>.nextEntities(reader: JsonReader): List<T> {
    val result = arrayListOf<T>()

    reader.beginArray()
    while (reader.hasNext()) {
        result.add(nextEntity(reader))
    }
    reader.endArray()

    return result
}

inline fun JsonReader.readEntity(fill: JsonReader.(String) -> Unit) {
    beginObject()
    while (hasNext()) {
        val name = nextName()
        fill(name);
    }
    endObject()
}

fun Cursor.toMutableMap(): MutableMap<String, Any> {
    val result = mutableMapOf<String, Any>()
    for (index in 0 until columnCount) {
        val name = getColumnName(index)
        when (getType(index)) {
            Cursor.FIELD_TYPE_BLOB -> result[name] = getBlob(index)
            Cursor.FIELD_TYPE_FLOAT -> result[name] = getDouble(index)
            Cursor.FIELD_TYPE_INTEGER -> result[name] = getLong(index)
            Cursor.FIELD_TYPE_STRING -> result[name] = getString(index)
//            Cursor.FIELD_TYPE_NULL -> result[name] = null
        }
    }
    return result
}

fun Map<String, Any>.toContentValues(): ContentValues {
    val result = ContentValues()
    for ((key, value) in this) {
        when (value) {
            is Int -> result.put(key, value)
            is Byte -> result.put(key, value)
            is Long -> result.put(key, value)
            is Float -> result.put(key, value)
            is Short -> result.put(key, value)
            is Double -> result.put(key, value)
            is String -> result.put(key, value)
            is Boolean -> result.put(key, value)
            is ByteArray -> result.put(key, value)
        }
    }
    return result
}