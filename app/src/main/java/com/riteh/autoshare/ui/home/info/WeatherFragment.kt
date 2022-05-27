package com.riteh.autoshare.ui.home.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.WeatherForecastAdapter
import com.riteh.autoshare.data.api.APIRequest
import com.riteh.autoshare.responses.weather.current.WeatherCurrentItem
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private var forecastList = mutableListOf<WeatherForecastItem>()
    lateinit var WeatherCurrentItem: WeatherCurrentItem
    var apiKey = "72a2a76b30dd2c83d3e9ca25905faa9c"
    var latitude: String = ""
    var longitude: String = ""


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
        //Log.d("API  ", currentWather.toString())
        makeAPIRequest()

        val view: View = inflater.inflate(R.layout.weather_fragment, container, false)
        //WeatherCurrentItem.name = view.findViewById<>()
        //Log.d("API  ", WeatherCurrentItem.name)
        //WeatherCurrentItem.name = (WeatherCurrentItem) view.findViewById(R.id.address)
        //val myTextView1 = (View)findViewById(R.id.address)
        //myTextView1.setText(WeatherCurrentItem.name)
        Log.d("name  ", WeatherCurrentItem.name)
        return view


       // return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("name1  ", WeatherCurrentItem.name)

        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        setUpRecyclerView() // this call should be inside api call once api funcionality is set up
}

    private fun setUpRecyclerView() {
        rv_days.layoutManager = GridLayoutManager(requireContext(), 1)
        rv_days.adapter = WeatherForecastAdapter(forecastList, requireContext())
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPIRequest() {

        //val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid="+apiKey
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        Log.d("BASE_URL ", BASE_URL)




        val api: APIRequest = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(APIRequest::class.java)


     GlobalScope.launch(Dispatchers.IO) {
         val call: Call<WeatherCurrentItem> = api.getCurrentWeather(latitude, longitude, apiKey)

         val response: Response<WeatherCurrentItem> = call.execute()
         val current: WeatherCurrentItem? = response.body()

         try {
             if (current != null) {
                 WeatherCurrentItem = current

             }
             Log.d("currentWather ", WeatherCurrentItem.name)
         }catch (e: Exception) {
             println(e.toString())
         }

     }
   }

}