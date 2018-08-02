package meugeninua.citiesnavigator.app.di

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.db.AppDatabaseHelper
import meugeninua.citiesnavigator.model.db.CitiesDao
import meugeninua.citiesnavigator.model.db.CitiesDaoImpl
import meugeninua.citiesnavigator.model.entities.*
import meugeninua.citiesnavigator.model.repositories.MainRepository
import meugeninua.citiesnavigator.model.repositories.MainRepositoryImpl
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
    factory { buildOkHttp() }
    factory { AppDatabaseHelper(get(APP_CONTEXT)) as SQLiteOpenHelper }
    bean { buildDispatcher(get(APP_CONTEXT)) }

    factory(CITY_ENTITY) { CityReader() as EntityReader<CityEntity> }
    factory(COUNTRY_ENTITY) { CountryReader() as EntityReader<CountryEntity> }
    bean { MainRepositoryImpl(get(CITY_ENTITY), get(COUNTRY_ENTITY), get()) as MainRepository }

    bean { CitiesDaoImpl(get()) as CitiesDao }
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

private fun buildDispatcher(context: Context): FirebaseJobDispatcher {
    return FirebaseJobDispatcher(GooglePlayDriver(context))
}