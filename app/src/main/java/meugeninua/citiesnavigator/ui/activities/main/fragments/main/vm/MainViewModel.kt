package meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.launch
import meugeninua.citiesnavigator.model.db.CitiesDao
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.model.repositories.MainRepository
import meugeninua.citiesnavigator.ui.activities.base.resource.Resource
import meugeninua.citiesnavigator.ui.activities.base.resource.toNullableData
import timber.log.Timber

/**
 * @author meugen
 */
class MainViewModel(
        private val dao: CitiesDao): ViewModel() {

    val liveData: MutableLiveData<Resource<MainData>> = MutableLiveData()

    fun loadCities(reload: Boolean = false) {
        if (reload || liveData.value == null) {
            internalLoadCities()
        }
    }

    private fun internalLoadCities() = launch {
        liveData.postValue(Resource.loading(liveData.value.toNullableData()))
        try {
            val cities = dao.cities()
            val countries = dao.countries()
                    .map { it.code to it }
                    .toMap()
            liveData.postValue(Resource.success(
                    MainData(cities, countries)))
        } catch (e: Throwable) {
            Timber.d(e)
            liveData.postValue(Resource.error(e))
        }
    }
}

class MainData(
        val cities: List<CityEntity>,
        val countries: Map<String, CountryEntity>)