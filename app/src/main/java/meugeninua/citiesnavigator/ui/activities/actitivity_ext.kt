package meugeninua.citiesnavigator.ui.activities

import android.app.Activity

/**
 * @author meugen
 */
fun Activity.doubleExtra(name: String) = lazy { intent.getDoubleExtra(name, 0.0) }