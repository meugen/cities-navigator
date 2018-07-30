package meugeninua.citiesnavigator.ui

import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.base.binding.BindingImpl
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBindingImpl
import org.koin.dsl.module.applicationContext

/**
 * @author meugen
 */
val uiModule = applicationContext {
    factory { BindingImpl() as Binding }
    factory { MainBindingImpl() as MainBinding }
}