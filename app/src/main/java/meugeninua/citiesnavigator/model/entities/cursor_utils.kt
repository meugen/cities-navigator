package meugeninua.citiesnavigator.model.entities

import android.database.Cursor

/**
 * @author meugen
 */
fun Cursor.getString(name: String): String = getString(getColumnIndexOrThrow(name))

fun Cursor.getDouble(name: String): Double = getDouble(getColumnIndexOrThrow(name))