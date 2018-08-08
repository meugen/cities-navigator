package meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters

import android.support.v4.util.ObjectsCompat
import android.support.v7.util.DiffUtil
import meugeninua.citiesnavigator.model.entities.CityEntity

/**
 * @author meugen
 */
class CityItemCallback: DiffUtil.ItemCallback<CityEntity>() {

    override fun areContentsTheSame(
            oldItem: CityEntity?,
            newItem: CityEntity?): Boolean {
        return ObjectsCompat.equals(oldItem, newItem)
    }

    override fun areItemsTheSame(
            oldItem: CityEntity?,
            newItem: CityEntity?): Boolean {
        return ObjectsCompat.equals(oldItem?.id, newItem?.id)
    }
}