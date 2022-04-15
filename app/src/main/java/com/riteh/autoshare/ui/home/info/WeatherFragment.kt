package com.riteh.autoshare.ui.home.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.WeatherForecastAdapter
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import kotlinx.android.synthetic.main.weather_fragment.*


class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    private var forecastList = mutableListOf<WeatherForecastItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        setUpRecyclerView() // this call should be inside api call once api funcionality is set up
}

    private fun setUpRecyclerView() {
        rv_days.layoutManager = GridLayoutManager(requireContext(), 1)
        rv_days.adapter = WeatherForecastAdapter(forecastList, requireContext())
    }

}