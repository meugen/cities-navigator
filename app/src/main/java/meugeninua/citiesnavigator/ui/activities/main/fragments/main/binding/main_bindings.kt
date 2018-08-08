package meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding

import android.content.Context
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.ui.activities.base.binding.BaseBinding
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesAdapter
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainData

/**
 * @author meugen
 */
interface MainBinding: Binding {

    fun setupRecycler(listener: CitiesAdapter.OnCitySelectedListener)

    fun displayCities(data: MainData)

    fun displayProgressBar()

    fun displayError(error: Throwable)
}

class MainBindingImpl(private val context: Context): BaseBinding(), MainBinding {

    private lateinit var adapter: CitiesAdapter

    override fun setupRecycler(listener: CitiesAdapter.OnCitySelectedListener) {
        adapter = CitiesAdapter(LayoutInflater.from(context), listener)

        val recycler: RecyclerView = get(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
        recycler.addItemDecoration(StickyRecyclerHeadersDecoration(adapter))
        recycler.adapter = adapter
    }

    override fun displayCities(data: MainData) {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.hide()
        adapter.updateAdapter(data.cities, data.countries)
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