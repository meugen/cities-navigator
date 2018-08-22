package meugeninua.citiesnavigator.ui.activities.main.fragments.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.app.citiesApp
import meugeninua.citiesnavigator.app.di.AppModule
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.ui.activities.base.fragments.BindingFragment
import meugeninua.citiesnavigator.ui.activities.base.resource.Resource
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesAdapter
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CityItemCallback
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBindingImpl
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainViewModel
import meugeninua.citiesnavigator.ui.activities.map.MapActivity
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required

/**
 * @author meugen
 */
class MainFragment: BindingFragment<MainBinding>(), CitiesAdapter.OnCitySelectedListener, Injects<MainFragmentModule> {

    override val binding: MainBinding by required { binding }
    val model: MainViewModel by required { model }

    override fun onAttach(context: Context) {
        inject(MainFragmentModule(this, context.citiesApp.module))
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main,
                container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.setupRecycler(this)
        binding.displayCities()

        model.liveData.observe(this, Observer { onData(it) })
        model.loadCountries()
    }

    private fun onData(resource: Resource<Map<String, CountryEntity>>?) {
        resource?.let {
            if (it.isLoading) {
                binding.displayProgressBar()
            } else try {
                binding.attachCountries(it.data)
            } catch (e: Throwable) {
                binding.displayError(e)
            }
        }
    }

    fun filterCities(text: CharSequence) {
        binding.displayCities(text.toString())
    }

    override fun onCitySelected(entity: CityEntity) {
        val context = this.context ?: return
        MapActivity.Builder()
                .withLat(entity.lat)
                .withLng(entity.lng)
                .withName(entity.name)
                .start(context)
    }
}

class MainFragmentModule(
        private val fragment: MainFragment,
        parent: AppModule): AppModule by parent {

    val binding: MainBinding
        get() = MainBindingImpl(
                context = fragment.context!!,
                dao = citiesDao,
                callback = CityItemCallback(),
                ioExecutor = ioExecutor,
                mainExecutor = mainExecutor)
    val model: MainViewModel
        get() = ViewModelProviders
                .of(fragment, FactoryImpl(this))
                .get(MainViewModel::class.java)

    class FactoryImpl(private val module: AppModule): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass === MainViewModel::class.java) {
                return MainViewModel(module.citiesDao) as T
            }
            throw IllegalArgumentException("Unknown model $modelClass")
        }
    }
}