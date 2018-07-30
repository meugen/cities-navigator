package meugeninua.citiesnavigator.ui.activities.base.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding

/**
 * @author meugen
 */
abstract class BindingFragment<B: Binding>: Fragment() {

    abstract val binding: B

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.attachView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.detachView()
    }
}