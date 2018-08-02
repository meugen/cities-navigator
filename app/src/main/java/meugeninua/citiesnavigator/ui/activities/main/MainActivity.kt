package meugeninua.citiesnavigator.ui.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.app.services.FetchAllService
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.MainFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dispatcher: FirebaseJobDispatcher by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchAllService.Launcher().launch(dispatcher)
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
