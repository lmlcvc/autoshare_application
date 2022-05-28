package com.riteh.autoshare.ui.home.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.WeatherForecastAdapter
import com.riteh.autoshare.data.api.APIRequest
import com.riteh.autoshare.responses.User
import com.riteh.autoshare.responses.weather.current.WeatherCurrentItem
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import com.riteh.autoshare.ui.home.MainActivity
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private var forecastList = mutableListOf<WeatherForecastItem>()
    private lateinit var WeatherCurrentItem: WeatherCurrentItem
    var apiKey = "72a2a76b30dd2c83d3e9ca25905faa9c"
    var latitude: String = ""
    var longitude: String = ""
    var units: String = "metric"


    companion object {
        fun newInstance(latitude: String, longitude: String) : WeatherFragment {
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
        Log.d("latitude ", latitude)
        Log.d("longitude ", longitude)
        //makeAPIRequest()

       return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        makeAPIRequest()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
        //setUpRecyclerView() // this call should be inside api call once api funcionality is set up
}

    private fun setUpRecyclerView() {
        rv_days.layoutManager = GridLayoutManager(context, 2)
        rv_days.adapter = WeatherForecastAdapter(forecastList, requireContext())
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPIRequest() {
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        Log.d("BASE_URL ", BASE_URL)

        val api: APIRequest = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)


     GlobalScope.launch(Dispatchers.IO) {
         val call: Call<WeatherCurrentItem> = api.getCurrentWeather(latitude, longitude, apiKey, units)

         val response: Response<WeatherCurrentItem> = call.execute()
         val current: WeatherCurrentItem? = response.body()

         try {
             if (current != null) {

                 withContext(Dispatchers.Main) {
                     writeResponseValues(current)
                 }
             }

         }catch (e: Exception) {
             println(e.toString())
         }
     }
   }


    @SuppressLint("SetTextI18n")
    private fun writeResponseValues(current: WeatherCurrentItem) {

        address.text = current.name + ", " + (current.sys?.country ?: "None")
        temp.text = (current.main.temp).roundToInt().toString() + "°C"
        temp_min.text = (current.main.temp_min).roundToInt().toString() + "°C" + " / "
        temp_max.text = (current.main.temp_max).roundToInt().toString() + "°C"
        status.text = current.weather[0].description

        sunrise.text = convertTime(current.sys?.sunrise)
        sunset.text = convertTime(current.sys?.sunset)

        humidity.text = current.main.humidity.toString() + " % "
        pressure.text = current.main.pressure.toString()  + " hPa"
        wind.text = current.wind.speed.toString() + " m/s"

        return
    }


    private fun convertTime(totalSecs: Int?): String? {
        val date = Date(totalSecs?.times(1000L) ?:0 )
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }

}