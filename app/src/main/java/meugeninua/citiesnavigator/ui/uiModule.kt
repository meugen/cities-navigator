package meugeninua.citiesnavigator.ui

import meugeninua.citiesnavigator.app.di.APP_CONTEXT
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.base.binding.BindingImpl
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBindingImpl
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainViewModel
import org.koin.dsl.module.applicationContext

/**
 * @author meugen
 */
val uiModule = applicationContext {
    factory { BindingImpl() as Binding }
    factory { MainBindingImpl(get(APP_CONTEXT)) as MainBinding }

    factory { MainViewModel(get()) }
}