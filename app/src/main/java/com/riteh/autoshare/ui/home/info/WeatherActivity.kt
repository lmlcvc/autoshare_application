package com.riteh.autoshare.ui.home.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        initPlacesFragment()
        setUpListeners()
    }

    private fun setUpListeners() {
        button.setOnClickListener {     // TODO: should pass location and make api call
            val fragment: Fragment = WeatherFragment.newInstance()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
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