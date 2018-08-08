package meugeninua.citiesnavigator.model.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.model.entities.toContentValues
import java.util.*

/**
 * @author meugen
 */
const val DEFAULT_PAGE_SIZE = 50

interface CitiesDao {

    fun cities(
            position: Int = 0,
            loadSize: Int = DEFAULT_PAGE_SIZE): List<CityEntity>

    fun insertCities(cities: List<CityEntity>)

    fun citiesCount(): Int

    fun countries(): List<CountryEntity>

    fun insertCountries(countries: List<CountryEntity>)

    fun countriesCount(): Int
}

class CitiesDaoImpl(helper: SQLiteOpenHelper): CitiesDao {

    private val db = helper.writableDatabase

    override fun cities(position: Int, loadSize: Int): List<CityEntity> {
        val sql = "SELECT * FROM cities ORDER BY country, name LIMIT ? OFFSET ?"
        val params: List<String> = listOf(
                "$loadSize", "$position")
        return db.select(sql, params) {
            val result = ArrayList<CityEntity>(it.count)
            while (it.moveToNext()) {
                result.add(CityEntity(it))
            }
            result
        }
    }

    override fun countries(): List<CountryEntity> {
        return db.select("SELECT * FROM countries") {
            val result = ArrayList<CountryEntity>(it.count)
            while (it.moveToNext()) {
                result.add(CountryEntity(it))
            }
            result
        }
    }

    override fun insertCities(cities: List<CityEntity>) {
        db.inTransaction {
            for (city in cities) {
                val values = city.toContentValues()
                insertOrThrow("cities",
                        null, values)
            }
        }
    }

    override fun insertCountries(countries: List<CountryEntity>) {
        db.inTransaction {
            for (country in countries) {
                val values = country.toContentValues()
                insertWithOnConflict("countries",
                        null, values,
                        SQLiteDatabase.CONFLICT_REPLACE)
            }
        }
    }

    override fun citiesCount(): Int {
        return db.select("SELECT count(id) c FROM cities") {
            var result = 0
            if (it.moveToFirst()) {
                result = it.getInt(0)
            }
            result
        }
    }

    override fun countriesCount(): Int {
        return db.select("SELECT count(code) c FROM countries") {
            var result = 0
            if (it.moveToFirst()) {
                result = it.getInt(0)
            }
            result
        }
    }
}

private inline fun <T> SQLiteDatabase.inTransaction(action: SQLiteDatabase.() -> T): T {
    beginTransaction()
    try {
        val result = action()
        setTransactionSuccessful()

        return result
    } finally {
        endTransaction()
    }
}

private inline fun <T> SQLiteDatabase.select(
        sql: String,
        params: List<String> = Collections.emptyList(),
        action: (Cursor) -> T): T {
    var cursor: Cursor? = null
    try {
        cursor = rawQuery(sql, params.toTypedArray())
        return action(cursor)
    } finally {
        cursor?.close()
    }
}