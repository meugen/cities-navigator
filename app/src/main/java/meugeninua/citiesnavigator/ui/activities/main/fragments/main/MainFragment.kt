package meugeninua.citiesnavigator.ui.activities.main.fragments.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.ui.activities.base.fragments.BindingFragment
import meugeninua.citiesnavigator.ui.activities.base.resource.Resource
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesAdapter
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainViewModel
import meugeninua.citiesnavigator.ui.activities.map.MapActivity
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

/**
 * @author meugen
 */
class MainFragment: BindingFragment<MainBinding>(), CitiesAdapter.OnCitySelectedListener {

    override val binding: MainBinding by inject()
    val model: MainViewModel by viewModel()

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

        model.liveData.observe(this, Observer { onCities(it) })
        model.loadCities(true)
    }

    private fun onCities(resource: Resource<List<CityEntity>>?) {
        resource?.let {
            if (it.isLoading) {
                binding.displayProgressBar()
            } else try {
                binding.displayCities(it.data)
            } catch (e: Throwable) {
                binding.displayError(e)
            }
        }
    }

    fun filterCities(text: CharSequence) {

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