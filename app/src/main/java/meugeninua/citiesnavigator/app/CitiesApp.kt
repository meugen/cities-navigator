package meugeninua.citiesnavigator.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import meugeninua.citiesnavigator.BuildConfig
import meugeninua.citiesnavigator.app.di.citiesModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * @author meugen
 */
class CitiesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Fabric.with(this, Crashlytics())
        startKoin(this, listOf(citiesModule))
    }
}