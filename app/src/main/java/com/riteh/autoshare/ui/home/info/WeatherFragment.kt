package com.riteh.autoshare.ui.home.info

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.WeatherForecastAdapter
import com.riteh.autoshare.data.api.APIRequest
import com.riteh.autoshare.responses.weather.current.WeatherCurrentItem
import com.riteh.autoshare.responses.weather.forecast.Daily
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.android.synthetic.main.weather_fragment.iv_close
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToInt


class WeatherFragment : Fragment() {
    private var forecastList = mutableListOf<Daily>()
    private lateinit var allForecast: WeatherForecastItem
    private lateinit var WeatherCurrentItem: WeatherCurrentItem
    var latitude: String = ""
    var longitude: String = ""
    var units: String = "metric"


    companion object {
        fun newInstance(latitude: String, longitude: String): WeatherFragment {
            val fragment = WeatherFragment()

            fragment.latitude = latitude
            fragment.longitude = longitude

            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }


    override fun onResume() {
        super.onResume()
        makeAPIRequest()
        makeAPIRequestWeek()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        iv_back.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        iv_close.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setUpRecyclerView() {
        rv_days.layoutManager = GridLayoutManager(context, 1)
        rv_days.adapter = WeatherForecastAdapter(forecastList, requireContext())
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPIRequest() {
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        val apiKey = getString(R.string.WEATHER_API_KEY)

        val api: APIRequest = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val call: Call<WeatherCurrentItem> =
                api.getCurrentWeather(latitude, longitude, apiKey, units)

            val response: Response<WeatherCurrentItem> = call.execute()
            val current: WeatherCurrentItem? = response.body()

            try {
                if (current != null) {

                    withContext(Dispatchers.Main) {
                        writeResponseValues(current)
                    }
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun writeResponseValues(current: WeatherCurrentItem) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            updated_at.text = java.time.format.DateTimeFormatter.ofPattern("LLL d, KK:mm a")
                .withLocale(Locale.UK)
                .withZone(ZoneId.of("UTC"))
                .format(java.time.Instant.ofEpochSecond(current.dt))
        }

        address.text = current.name + ", " + (current.sys?.country ?: "None")
        temp.text = (current.main.temp).roundToInt().toString() + "°C"
        temp_min.text = (current.main.temp_min).roundToInt().toString() + "°C" + " / "
        temp_max.text = (current.main.temp_max).roundToInt().toString() + "°C"
        status.text = current.weather[0].description

        sunrise.text = convertTime(current.sys?.sunrise)
        sunset.text = convertTime(current.sys?.sunset)

        humidity.text = current.main.humidity.toString() + " % "
        pressure.text = current.main.pressure.toString() + " hPa"
        wind.text = current.wind.speed.toString() + " m/s"

        return
    }


    @SuppressLint("SimpleDateFormat")
    private fun convertTime(epochSeconds: Long?): String? {
        val date = Date(epochSeconds?.times(1000) ?: 0)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPIRequestWeek() {
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        val apiKey = getString(R.string.WEATHER_API_KEY)

        val api: APIRequest = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(APIRequest::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            val call: Call<WeatherForecastItem> =
                api.getCurrentWeatherWeek(latitude, longitude, apiKey, units)

            val response: Response<WeatherForecastItem> = call.execute()
            val allForecast: WeatherForecastItem? = response.body()

            try {
                if (allForecast != null) {
                    for (item in allForecast.daily) {
                        forecastList.add(item)
                    }

                    withContext(Dispatchers.Main) {
                        setUpRecyclerView()
                    }
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }
}