package meugeninua.citiesnavigator.app.di

import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.base.binding.BindingImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * @author meugen
 */
const val APP_CONTEXT = "app_context"

val citiesModule: Module = applicationContext {
    factory(APP_CONTEXT) { androidApplication() }
    factory { BindingImpl() as Binding }
}