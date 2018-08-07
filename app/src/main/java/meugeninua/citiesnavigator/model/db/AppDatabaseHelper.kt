package meugeninua.citiesnavigator.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.Reader
import java.util.regex.Pattern

/**
 * @author meugen
 */
private const val NAME = "cities_navigator"
private const val VERSION = 2

class AppDatabaseHelper(context: Context)
        : SQLiteOpenHelper(context, NAME, null, VERSION) {

    private val assets = context.assets
    private var pattern: Pattern? = null

    override fun onCreate(db: SQLiteDatabase?) {
        onUpgrade(db, 0, VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            pattern = Pattern.compile("\\s*([^;]+);")
            try {
                for (version in oldVersion + 1..newVersion) {
                    upgrade(it, version)
                }
            } finally {
                pattern = null
            }
        }
    }

    private fun upgrade(db: SQLiteDatabase, version: Int) {
        pattern?.let {
            val matcher = it.matcher(fetchSql(version))
            while (matcher.find()) {
                db.execSQL(matcher.group(1))
            }
        }
    }

    private fun fetchSql(version: Int): CharSequence {
        var reader: Reader? = null
        try {
            reader = assets.open("db/${NAME}/${version}.sql")
                    .reader()
            return reader.readText()
        } finally {
            reader?.close()
        }
    }
}