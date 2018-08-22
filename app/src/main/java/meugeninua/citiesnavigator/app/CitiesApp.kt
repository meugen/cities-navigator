package meugeninua.citiesnavigator.app

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.app.di.AppModule
import meugeninua.citiesnavigator.app.di.AppModuleImpl
import timber.log.Timber

/**
 * @author meugen
 */
class CitiesApp: Application() {

    lateinit var module: AppModule

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Fabric.with(this, Crashlytics())
        module = AppModuleImpl(this)
    }
}

val Context.citiesApp
        get() = applicationContext as CitiesApp