package meugeninua.citiesnavigator.app.di

import android.content.Context
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.entities.*
import meugeninua.citiesnavigator.model.repositories.CityRepository
import meugeninua.citiesnavigator.model.repositories.CityRepositoryImpl
import meugeninua.citiesnavigator.model.repositories.CountryRepository
import meugeninua.citiesnavigator.model.repositories.CountryRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * @author meugen
 */
const val APP_CONTEXT = "app_context"

val appModule: Module = applicationContext {
    factory(APP_CONTEXT) { androidApplication() as Context }
    bean { buildOkHttp() }

    factory(CITY_ENTITY) { CityReader() as EntityReader<CityEntity> }
    bean { CityRepositoryImpl(get(CITY_ENTITY), get()) as CityRepository }

    factory(COUNTRY_ENTITY) { CountryReader() as EntityReader<CountryEntity> }
    bean { CountryRepositoryImpl(get(COUNTRY_ENTITY)) as CountryRepository }
}

private fun buildOkHttp(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        builder.addInterceptor(interceptor)
    }
    return builder.build()
}