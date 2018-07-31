package meugeninua.citiesnavigator.model.repositories

import android.util.JsonReader
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.model.nextEntities
import java.net.URL

/**
 * @author meugen
 */
interface CountryRepository {

    fun countries(): List<CountryEntity>
}

class CountryRepositoryImpl(
        private val countryReader: EntityReader<CountryEntity>): CountryRepository {

    override fun countries(): List<CountryEntity> {
        val jsonReader = JsonReader(URL(BuildConfig.COUNTRIES_URL)
                .openStream().bufferedReader())
        return countryReader.nextEntities(jsonReader)
    }
}