package meugeninua.citiesnavigator.ui.activities.main.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.model.EntityReader
import meugeninua.citiesnavigator.model.entities.CITY_READER
import meugeninua.citiesnavigator.model.entities.COUNTRY_READER
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.ui.activities.base.fragments.BindingFragment
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @author meugen
 */
class MainFragment: BindingFragment<MainBinding>() {

    override val binding: MainBinding by inject()
    val model: MainViewModel by viewModel()
    val cityReader: EntityReader<CityEntity> by inject(CITY_READER)
    val countryReader: EntityReader<CountryEntity> by inject(COUNTRY_READER)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main,
                container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.displayMessage("Sample text")

        Timber.d("cityReader: %s", cityReader)
        Timber.d("countryReader: %s", countryReader)
    }
}