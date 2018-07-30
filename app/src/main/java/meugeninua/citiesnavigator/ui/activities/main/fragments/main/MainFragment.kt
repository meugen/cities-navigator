package meugeninua.citiesnavigator.ui.activities.main.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import meugeninua.citiesnavigator.ui.activities.base.fragments.BindingFragment
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding.MainBinding
import meugeninua.citiesnavigator.R
import org.koin.android.ext.android.inject

/**
 * @author meugen
 */
class MainFragment: BindingFragment<MainBinding>() {

    override val binding: MainBinding by inject()

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
    }
}