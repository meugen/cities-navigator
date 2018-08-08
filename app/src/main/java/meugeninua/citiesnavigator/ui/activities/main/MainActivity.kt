package meugeninua.citiesnavigator.ui.activities.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.app.services.FetchAllService
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.MainFragment
import org.koin.android.ext.android.inject
import java.lang.ref.WeakReference

private const val WHAT_PROCESS_QUERY = 1

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dispatcher: FirebaseJobDispatcher by inject()
    private val handler by lazy { HandlerImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchAllService.Launcher().launch(dispatcher)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchView = menu!!.findItem(R.id.search)
                .actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null) {
            return false
        }
        handler.removeMessages(WHAT_PROCESS_QUERY)
        val message = handler.obtainMessage(WHAT_PROCESS_QUERY, newText)
        handler.sendMessageDelayed(message, 500L);
        return true
    }

    private fun processQuery(query: String) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main)
                as MainFragment
        fragment.filterCities(query)
    }

    class HandlerImpl(activity: MainActivity): Handler(Looper.getMainLooper()) {

        private val activityRef = WeakReference(activity)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val activity = activityRef.get()
            if (activity == null || msg == null) {
                return
            }
            if (msg.what == WHAT_PROCESS_QUERY) {
                activity.processQuery(msg.obj as String)
            }
        }
    }
}
