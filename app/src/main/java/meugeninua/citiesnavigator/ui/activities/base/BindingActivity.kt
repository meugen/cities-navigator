package meugeninua.citiesnavigator.ui.activities.base

import android.support.v7.app.AppCompatActivity
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding

/**
 * @author meugen
 */
abstract class BindingActivity<B: Binding>: AppCompatActivity() {

    abstract val binding: B

    override fun onContentChanged() {
        super.onContentChanged()
        binding.attachView(window.decorView)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.detachView()
    }
}