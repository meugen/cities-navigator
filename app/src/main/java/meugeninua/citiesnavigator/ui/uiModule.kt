package meugeninua.citiesnavigator.ui

import android.arch.paging.DataSource
import android.support.v7.util.DiffUtil
import meugeninua.citiesnavigator.app.di.APP_CONTEXT
import meugeninua.citiesnavigator.app.di.IO_EXECUTOR
import meugeninua.citiesnavigator.app.di.MAIN_EXECUTOR
import meugeninua.citiesnavigator.model.entities.CITY_ENTITY
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.base.binding.BindingImpl
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesSource
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CityItemCallback
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBindingImpl
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainViewModel
import org.koin.dsl.module.applicationContext

/**
 * @author meugen
 */
val uiModule = applicationContext {
    factory { BindingImpl() as Binding }
    factory { MainBindingImpl(
            context = get(APP_CONTEXT),
            dao = get(),
            callback = get(CITY_ENTITY),
            ioExecutor = get(IO_EXECUTOR),
            mainExecutor = get(MAIN_EXECUTOR)) as MainBinding }
    factory(CITY_ENTITY) { CitiesSource(get()) as DataSource<Int, CityEntity> }
    factory(CITY_ENTITY) { CityItemCallback() as DiffUtil.ItemCallback<CityEntity> }

    factory { MainViewModel(get()) }
}