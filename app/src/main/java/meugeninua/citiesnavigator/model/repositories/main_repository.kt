package meugeninua.citiesnavigator.model.repositories

import android.util.JsonReader
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.await
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.model.nextEntities
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * @author meugen
 */
interface MainRepository {

    suspend fun cities(): List<CityEntity>

    suspend fun countries(): List<CountryEntity>
}

class MainRepositoryImpl(
        private val cityReader: EntityReader<CityEntity>,
        private val countryReader: EntityReader<CountryEntity>,
        private val client: OkHttpClient): MainRepository {

    override suspend fun cities(): List<CityEntity> {
        val request = Request.Builder()
                .get().url(BuildConfig.CITIES_URL)
                .build()
        val call = client.newCall(request)
        val body = call.await().body()
                ?: throw IllegalArgumentException("No body in response")
        val jsonReader = JsonReader(body.charStream())
        return cityReader.nextEntities(jsonReader)
    }

    override suspend fun countries(): List<CountryEntity> {
        val request = Request.Builder()
                .get().url(BuildConfig.COUNTRIES_URL)
                .build()
        val call = client.newCall(request)
        val body = call.await().body()
                ?: throw IllegalArgumentException("No body in response")
        val jsonReader = JsonReader(body.charStream())
        return countryReader.nextEntities(jsonReader)
    }
}