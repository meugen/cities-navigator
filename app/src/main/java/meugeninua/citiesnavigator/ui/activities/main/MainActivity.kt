package meugeninua.citiesnavigator.ui.activities.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding: Binding by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = binding.get(R.id.message)
        textView.text = "Sample text"
    }

    override fun onContentChanged() {
        super.onContentChanged()
        binding.attachView(window.decorView)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.detachView()
    }
}
