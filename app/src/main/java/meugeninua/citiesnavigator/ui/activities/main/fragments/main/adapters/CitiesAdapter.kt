package meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters

import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.model.entities.CityEntity
import meugeninua.citiesnavigator.model.entities.CountryEntity
import java.util.*

/**
 * @author meugen
 */
class CitiesAdapter(
            private val inflater: LayoutInflater,
            private val listener: OnCitySelectedListener,
            callback: DiffUtil.ItemCallback<CityEntity>)
        : PagedListAdapter<CityEntity, CitiesAdapter.CityHolder>(callback),
        StickyRecyclerHeadersAdapter<CitiesAdapter.CountryHolder> {

    private var countries: Map<String, CountryEntity> = Collections.emptyMap()

    fun attachCountries(countries: Map<String, CountryEntity>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view = inflater.inflate(R.layout.item_city, parent, false)
        return CityHolder(view, listener)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    private fun getCountry(position: Int): CountryEntity? {
        val code = getItem(position)?.country
                ?: return null
        return countries[code]
    }

    override fun getHeaderId(position: Int): Long {
        val country = getCountry(position)
                ?: return -1L
        return country.id.toLong()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): CountryHolder {
        val view = inflater.inflate(R.layout.item_country, parent, false)
        return CountryHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: CountryHolder, position: Int) {
        val country = getCountry(position)
                ?: return
        holder.bind(country)
    }

    class CityHolder(view: View, private val l: OnCitySelectedListener)
            : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val nameView: TextView = view.findViewById(R.id.name)

        private lateinit var entity: CityEntity

        init {
            nameView.setOnClickListener(this)
        }

        fun bind(entity: CityEntity) {
            this.entity = entity
            nameView.text = entity.name
        }

        override fun onClick(v: View?) {
            val viewId = v?.id ?: return
            when (viewId) {
                R.id.name -> l.onCitySelected(entity)
            }
        }
    }

    class CountryHolder(view: View): RecyclerView.ViewHolder(view) {

        private val countryView: TextView = view.findViewById(R.id.country)

        fun bind(entity: CountryEntity) {
            countryView.text = entity.name
        }
    }

    interface OnCitySelectedListener {

        fun onCitySelected(entity: CityEntity)
    }
}