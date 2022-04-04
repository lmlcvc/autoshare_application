package com.riteh.autoshare.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.riteh.autoshare.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_location_input.*
import kotlinx.android.synthetic.main.search_fragment.*


class LocationInputActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var viewModel: SearchViewModel

    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private lateinit var mapFragment: SupportMapFragment

    private lateinit var map: GoogleMap
    private val initialPosition = LatLng(45.37, 14.35)
    private var marker: MarkerOptions = MarkerOptions().position(initialPosition)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_input)

        viewModel = vita.with(VitaOwner.Multiple(this)).getViewModel()

        initPlacesFragment()
        initMapFragment()
        setUpListeners()
    }

    private fun setUpListeners() {
        button.setOnClickListener {
            this.finish()
        }

        iv_close.setOnClickListener {
            viewModel.setLocation("Pick a location")
            this.finish()
        }

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                viewModel.setLocation(place.name!!)

                map.clear()
                marker.position(place.latLng!!)
                map.addMarker(marker)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15F))
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
        map = googleMap
        map.addMarker(marker)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15F))
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
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG
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