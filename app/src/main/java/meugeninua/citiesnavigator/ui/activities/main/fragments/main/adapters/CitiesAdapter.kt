package meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.model.entities.CityEntity
import java.util.*

/**
 * @author meugen
 */
class CitiesAdapter(
            private val inflater: LayoutInflater,
            private val listener: OnCitySelectedListener)
        : RecyclerView.Adapter<CitiesAdapter.CityHolder>() {

    private var items: List<CityEntity> = Collections.emptyList()

    fun swapItems(newItems: List<CityEntity>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view = inflater.inflate(R.layout.item_city, parent, false)
        return CityHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(items[position])
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

    interface OnCitySelectedListener {

        fun onCitySelected(entity: CityEntity)
    }
}