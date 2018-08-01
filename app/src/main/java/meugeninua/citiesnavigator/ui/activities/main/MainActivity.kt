package meugeninua.citiesnavigator.ui.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.MainFragment

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) {
            return false
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.main) as MainFragment
        fragment.filterCities(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
