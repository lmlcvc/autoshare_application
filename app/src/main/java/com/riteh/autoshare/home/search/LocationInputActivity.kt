package com.riteh.autoshare.home.search

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.activity_location_input.*
import java.io.IOException
import java.util.*


class LocationInputActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var context: LocationInputActivity

    private lateinit var viewModel: SearchViewModel
    private lateinit var lastLocation: String

    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private lateinit var mapFragment: SupportMapFragment

    private lateinit var map: GoogleMap
    private var initialPosition = LatLng(45.37, 14.35)
    private var marker: MarkerOptions = MarkerOptions().position(initialPosition)

    private lateinit var geocoder: Geocoder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_input)

        context = this

        viewModel = vita.with(VitaOwner.Multiple(this)).getViewModel()
        lastLocation = viewModel.location.value.toString()

        geocoder = Geocoder(applicationContext, Locale.getDefault())


        initPlacesFragment()
        initMapFragment()

        setUpListeners()
    }

    /**
     * If a location had already been picked (and written to viewmodel), set it as default location.
     */
    private fun setDefaultLoc() {
        if (viewModel.location.value.toString() != getString(R.string.pick_a_location)) {
            val location = geocoder.getFromLocationName(viewModel.location.value.toString(), 1)
            marker.position(LatLng(location[0].latitude, location[0].longitude))
        }
    }

    /**
     * Syncing button clicks, location change via map and location change through search.
     */
    private fun setUpListeners() {
        button.setOnClickListener {
            if (viewModel.location.value.toString() == getString(R.string.pick_a_location)) {
                val locality = getLocalityFromLatLng(
                    map.cameraPosition.target.latitude,
                    map.cameraPosition.target.longitude
                )

                viewModel.setLocation(locality)
            }
            this.finish()
        }

        iv_back.setOnClickListener {
            viewModel.setLocation(lastLocation)
            this.finish()
        }

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                map.clear()
                marker.position(place.latLng!!)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 12F))

                button.setOnClickListener {
                    viewModel.setLocation(place.name!!)
                    context.finish()
                }

            }

            override fun onError(status: Status) {
                Toast.makeText(applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Get the SupportMapFragment and request notification when the map is ready to be used.
     */
    private fun initMapFragment() {
        mapFragment = (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)!!
        mapFragment.getMapAsync(this)
    }

    /**
     * Map setup, setting search string to default location.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setDefaultLoc()
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 12F))

        map.setOnCameraIdleListener {       // can only be set up when map is ready
            val locality = getLocalityFromLatLng(
                map.cameraPosition.target.latitude,
                map.cameraPosition.target.longitude
            )

            autocompleteFragment.setText(locality)

            button.setOnClickListener {
                if (locality != "") {
                    viewModel.setLocation(locality)
                    context.finish()
                } else {
                    Toast.makeText(this, "Location invalid", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getLocalityFromLatLng(lat: Double, lng: Double): String {
        val location: String
        try {
            val addressList: List<Address> = geocoder.getFromLocation(lat, lng, 1)

            if (addressList.isNotEmpty()) {
                return try {
                    location = addressList[0].locality
                    location
                } catch (e: Exception) {
                    ""
                }
            }
        } catch (e: IOException) {
            return ""
        }
        return ""
    }


    /**
     * Places search fragment and parameters setup.
     */
    private fun initPlacesFragment() {
        initPlacesClient()

        autocompleteFragment =
            (supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment?)!!
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG
            )
        )
    }

    private fun initPlacesClient() {
        val apiKey = getString(R.string.MAPS_API_KEY)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        Places.createClient(this)
    }

}