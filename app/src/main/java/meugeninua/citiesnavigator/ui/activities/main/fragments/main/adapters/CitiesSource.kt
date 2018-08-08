package meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters

import android.arch.paging.PositionalDataSource
import meugeninua.citiesnavigator.model.db.CitiesDao
import meugeninua.citiesnavigator.model.entities.CityEntity

/**
 * @author meugen
 */
class CitiesSource(
        private val dao: CitiesDao,
        private val filter: String = ""): PositionalDataSource<CityEntity>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CityEntity>) {
        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CityEntity>) {
        val totalCount = dao.citiesCount(filter)
        val position = computeInitialLoadPosition(params, totalCount)
        val loadSize = computeInitialLoadSize(params, position, totalCount)
        callback.onResult(loadRangeInternal(position, loadSize), position, totalCount)
    }

    private fun loadRangeInternal(position: Int, loadSize: Int): List<CityEntity> {
        return dao.cities(filter, position, loadSize)
    }
}