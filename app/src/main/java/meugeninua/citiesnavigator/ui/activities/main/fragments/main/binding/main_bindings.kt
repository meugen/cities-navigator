package meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding

import android.arch.paging.PagedList
import android.content.Context
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.model.db.CitiesDao
import meugeninua.citiesnavigator.model.db.DEFAULT_PAGE_SIZE
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import meugeninua.citiesnavigator.ui.activities.base.binding.BaseBinding
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesAdapter
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesSource
import java.util.concurrent.Executor

/**
 * @author meugen
 */
interface MainBinding: Binding {

    fun setupRecycler(listener: CitiesAdapter.OnCitySelectedListener)

    fun displayCities(query: String = "")

    fun attachCountries(countries: Map<String, CountryEntity>)

    fun displayProgressBar()

    fun displayError(error: Throwable)
}

class MainBindingImpl(
        private val context: Context,
        private val dao: CitiesDao,
        private val callback: DiffUtil.ItemCallback<CityEntity>,
        private val ioExecutor: Executor,
        private val mainExecutor: Executor): BaseBinding(), MainBinding {

    private lateinit var adapter: CitiesAdapter

    override fun setupRecycler(listener: CitiesAdapter.OnCitySelectedListener) {
        adapter = CitiesAdapter(LayoutInflater.from(context), listener, callback)

        val recycler: RecyclerView = get(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
        recycler.addItemDecoration(StickyRecyclerHeadersDecoration(adapter))
        recycler.adapter = adapter
    }

    override fun displayCities(query: String) {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.hide()
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(DEFAULT_PAGE_SIZE)
                .build()
        val list = PagedList.Builder<Int, CityEntity>(CitiesSource(dao, query), config)
                .setFetchExecutor(ioExecutor)
                .setNotifyExecutor(mainExecutor)
                .build()
        adapter.submitList(list)
    }

    override fun attachCountries(countries: Map<String, CountryEntity>) {
        adapter.attachCountries(countries);
    }

    override fun displayProgressBar() {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.show()
    }

    override fun displayError(error: Throwable) {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.hide()
        get<RecyclerView>(R.id.recycler).visibility = View.GONE
        val errorView: TextView = get(R.id.error)
        errorView.text = context.getText(R.string.message_error)
        errorView.visibility = View.VISIBLE
    }
}