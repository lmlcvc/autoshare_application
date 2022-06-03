package com.riteh.autoshare.ui.home.search

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.CarListAdapter
import com.riteh.autoshare.data.api.APIRequest
import com.riteh.autoshare.data.dataholders.Vehicle
import kotlinx.android.synthetic.main.activity_cars_list.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class CarsListActivity : AppCompatActivity() {
    private var carsList = mutableListOf<Vehicle>()
    var radius: Double = 20.00

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars_list)

        viewModel = vita.with(VitaOwner.Multiple(this)).getViewModel()

        setUpRecyclerView()
        setOnClickListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        makeAPIRequest()
    }

    private fun setUpRecyclerView() {
        rv_cars.layoutManager = LinearLayoutManager(this)
        rv_cars.adapter = CarListAdapter(carsList, this)
    }

    private fun setOnClickListeners() {
        bt_filter.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPIRequest() {
        val BASE_URL = getString(R.string.AUTOSHARE_API)

        val api: APIRequest = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getCarList(
                    viewModel.startDate.value!!,
                    viewModel.endDate.value!!,
                    viewModel.locationLatLng.value!!.latitude,
                    viewModel.locationLatLng.value!!.longitude,
                    radius
                )
                Log.d("response", response.toString())

                for (item in response.data) {
                    carsList.add(item)
                }

                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }
}