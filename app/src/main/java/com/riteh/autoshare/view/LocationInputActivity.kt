package com.riteh.autoshare.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.google.android.gms.common.api.Status
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
import com.riteh.autoshare.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_location_input.*


class LocationInputActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var viewModel: SearchViewModel
    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_input)

        //viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel = vita.with(VitaOwner.Multiple(this)).getViewModel()

        // call function "sendMessage" defined in SharedVieModel
        // to store the value in message.
        initPlacesFragment()
        initMapFragment()
        setUpListeners()
    }

    private fun setUpListeners() {
        button.setOnClickListener {
            this.finish()
        }

        iv_close.setOnClickListener {
            this.finish()
        }

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Toast.makeText(applicationContext, place.name, Toast.LENGTH_SHORT).show()
                viewModel.setLocation(place.name!!)
            }

            override fun onError(status: Status) {
                Toast.makeText(applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initMapFragment() {
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        mapFragment = (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)!!
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
    }

    private fun initPlacesFragment() {
        initPlacesClient()

        autocompleteFragment =
            (supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment?)!!
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS
            )
        )
    }

    private fun initPlacesClient() {
        val apiKey = getString(R.string.places_api_key)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        Places.createClient(this)
    }

}