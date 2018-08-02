package meugeninua.citiesnavigator.ui.activities.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.ui.activities.doubleExtra
import meugeninua.citiesnavigator.ui.activities.stringExtra

/**
 * @author meugen
 */
private const val EXTRA_LAT = "lat"
private const val EXTRA_LNG = "lng"
private const val EXTRA_NAME = "name"

private const val MAP_TAG = "map"

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private val lat: Double by doubleExtra(EXTRA_LAT)
    private val lng: Double by doubleExtra(EXTRA_LNG)
    private val name: String by stringExtra(EXTRA_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        var mapFragment = supportFragmentManager.findFragmentByTag(MAP_TAG) as SupportMapFragment?
        if (mapFragment == null) {
            val options = GoogleMapOptions()
                    .compassEnabled(true)
                    .zoomControlsEnabled(true)
                    .zoomGesturesEnabled(true)
            mapFragment = SupportMapFragment.newInstance(options)
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, mapFragment, MAP_TAG)
                    .commit()
        }
        mapFragment?.getMapAsync(this)

        title = name
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap!!
        moveToCity()
    }

    private fun moveToCity() {
        val update = CameraUpdateFactory
                .newLatLngZoom(LatLng(lat, lng), 15.0f)
        googleMap.animateCamera(update)
    }

    class Builder {

        private val extras = Bundle()

        fun withLat(lat: Double): Builder {
            extras.putDouble(EXTRA_LAT, lat)
            return this
        }

        fun withLng(lng: Double): Builder {
            extras.putDouble(EXTRA_LNG, lng)
            return this
        }

        fun withName(name: String): Builder {
            extras.putString(EXTRA_NAME, name)
            return this
        }

        private fun build(context: Context): Intent {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtras(extras)
            return intent
        }

        fun start(context: Context) {
            context.startActivity(build(context))
        }
    }
}