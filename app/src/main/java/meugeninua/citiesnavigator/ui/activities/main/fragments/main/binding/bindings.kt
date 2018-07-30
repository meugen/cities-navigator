package meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding

import android.widget.TextView
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.ui.activities.base.binding.BaseBinding
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding

/**
 * @author meugen
 */
interface MainBinding: Binding {

    fun displayMessage(msg: String)
}

class MainBindingImpl: BaseBinding(), MainBinding {

    override fun displayMessage(msg: String) {
        val textView = get<TextView>(R.id.message);
        textView.text = msg
    }
}