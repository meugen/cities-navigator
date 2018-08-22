package meugeninua.citiesnavigator.app.di

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.app.CitiesApp
import meugeninua.citiesnavigator.app.executors.MainThreadExecutor
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.db.AppDatabaseHelper
import meugeninua.citiesnavigator.model.db.CitiesDao
import meugeninua.citiesnavigator.model.db.CitiesDaoImpl
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CityReader
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.model.entities.CountryReader
import meugeninua.citiesnavigator.model.repositories.MainRepository
import meugeninua.citiesnavigator.model.repositories.MainRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author meugen
 */
class AppModuleImpl(private val app: CitiesApp): AppModule {

    private val openHelper: SQLiteOpenHelper
        get() = AppDatabaseHelper(app)
    private val cityReader: EntityReader<CityEntity>
        get() = CityReader()
    private val countryReader: EntityReader<CountryEntity>
        get() = CountryReader()
    private val httpClient: OkHttpClient
        get() = buildOkHttp()

    override val citiesDao: CitiesDao by lazy(LazyThreadSafetyMode.NONE) { CitiesDaoImpl(openHelper) }
    override val mainExecutor: Executor by lazy(LazyThreadSafetyMode.NONE) { MainThreadExecutor() }
    override val ioExecutor: Executor by lazy(LazyThreadSafetyMode.NONE) { Executors.newCachedThreadPool() }
    override val mainRepository: MainRepository by lazy(LazyThreadSafetyMode.NONE) { MainRepositoryImpl(cityReader, countryReader, httpClient) }
    override val dispatcher: FirebaseJobDispatcher by lazy(LazyThreadSafetyMode.NONE) { buildDispatcher(app) }
}

interface AppModule {

    val citiesDao: CitiesDao
    val mainExecutor: Executor
    val ioExecutor: Executor
    val mainRepository: MainRepository
    val dispatcher: FirebaseJobDispatcher
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