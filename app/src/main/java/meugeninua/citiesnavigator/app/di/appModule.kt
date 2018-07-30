package meugeninua.citiesnavigator.app.di

import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.entities.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * @author meugen
 */
const val APP_CONTEXT = "app_context"

val appModule: Module = applicationContext {
    factory(APP_CONTEXT) { androidApplication() }
    bean(CITY_READER) { CityReader() as EntityReader<CityEntity> }
    bean(COUNTRY_READER) { CountryReader() as EntityReader<CountryEntity> }
}