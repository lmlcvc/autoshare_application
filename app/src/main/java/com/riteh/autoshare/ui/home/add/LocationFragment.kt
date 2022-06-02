package com.riteh.autoshare.ui.home.add

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
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
import com.riteh.autoshare.databinding.FragmentLocationBinding
import kotlinx.android.synthetic.main.activity_location_input.*
import kotlinx.android.synthetic.main.fragment_location.btn_next
import kotlinx.android.synthetic.main.fragment_location.btn_prev
import kotlinx.android.synthetic.main.fragment_location.iv_close
import java.io.IOException
import java.util.*


class LocationFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentLocationBinding? = null
    private val sharedViewModel: AddViewModel by activityViewModels()

    private lateinit var context: AddActivity

    private lateinit var lastLocation: String
    private lateinit var lastLocationLatLng: LatLng

    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private lateinit var mapFragment: SupportMapFragment

    private lateinit var map: GoogleMap
    private var initialPosition = LatLng(45.37, 14.35)
    private var marker: MarkerOptions = MarkerOptions().position(initialPosition)

    private lateinit var geocoder: Geocoder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context = requireContext() as AddActivity
        lastLocation = sharedViewModel.location.value.toString()
        lastLocationLatLng = sharedViewModel.locationLatLng.value!!
        geocoder = Geocoder(requireActivity().applicationContext, Locale.getDefault())


        initPlacesFragment()
        initMapFragment()

        setUpListeners()
    }

    /**
     * Get the SupportMapFragment and request notification when the map is ready to be used.
     */
    private fun initMapFragment() {
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)!!
        mapFragment.getMapAsync(this)
    }


    /**
     * If a location had already been picked (and written to viewmodel), set it as default location.
     */
    private fun setDefaultLoc() {
        if (sharedViewModel.location.value.toString() != "") {
            val location =
                geocoder.getFromLocationName(sharedViewModel.location.value.toString(), 1)
            marker.position(LatLng(location[0].latitude, location[0].longitude))
        }
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

            btn_next.setOnClickListener {
                if (locality != "") {
                    sharedViewModel.setLocation(locality)
                    sharedViewModel.setLocationLatLng(
                        LatLng(
                            map.cameraPosition.target.latitude,
                            map.cameraPosition.target.longitude
                        )
                    )
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_locationFragment_to_dateFragment)
                } else {
                    Toast.makeText(requireContext(), "Location invalid", Toast.LENGTH_SHORT).show()
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
            childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment

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
            Places.initialize(requireActivity().applicationContext, apiKey)
        }
        Places.createClient(requireActivity())
    }


    private fun setUpListeners() {
        iv_close.setOnClickListener {
            requireActivity().finish()
        }

        btn_prev.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_locationFragment_to_priceFragment)
        }

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                map.clear()
                marker.position(place.latLng!!)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 12F))

                sharedViewModel.setLocation(place.name!!)
                sharedViewModel.setLocationLatLng(place.latLng!!)
            }

            override fun onError(status: Status) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    status.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}