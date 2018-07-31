package meugeninua.citiesnavigator.model.repositories

import android.util.JsonReader
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.nextEntities
import java.net.URL

/**
 * @author meugen
 */
interface CityRepository {

    fun cities(): List<CityEntity>
}

class CityRepositoryImpl(
        private val cityReader: EntityReader<CityEntity>): CityRepository {

    override fun cities(): List<CityEntity> {
        val jsonReader = JsonReader(URL(BuildConfig.CITIES_URL)
                .openStream().bufferedReader())
        return cityReader.nextEntities(jsonReader)
    }
}