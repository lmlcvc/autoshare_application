package com.riteh.autoshare.ui.home.user.vehicles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.VehicleListAdapter
import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.network.VehicleApi
import com.riteh.autoshare.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_vehicle_list.*
import kotlinx.android.synthetic.main.activity_vehicle_list.rv_vehicles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class VehicleListActivity : AppCompatActivity() {

    private lateinit var adapter: VehicleListAdapter
    private var vehiclesList = mutableListOf<Vehicle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        makeAPIRequestWithOwnerID()
        setOnClickListeners()
    }


    override fun onResume() {
        super.onResume()
        setUpRecyclerView(vehiclesList)
    }


    private fun makeAPIRequestWithOwnerID() {
        val userPreferences = UserPreferences(this)

        GlobalScope.launch(
            Dispatchers.IO
        ) {
            userPreferences.getUserFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    makeAPIRequest(it.id)
                }
            }
        }
    }


    private fun makeAPIRequest(id: Int) {
        val api: VehicleApi = Retrofit.Builder()
            .baseUrl(getString(R.string.AUTOSHARE_API))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(VehicleApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getVehiclesByUserId(id)

                for (item in response.data) {
                    vehiclesList.add(item)
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView(vehiclesList)
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }


    /**
     * Set up recyclerview with list of user's vehicles.
     */
    private fun setUpRecyclerView(vehicles: MutableList<Vehicle>) {
        rv_vehicles.layoutManager = GridLayoutManager(this, 1)

        adapter = VehicleListAdapter(vehicles, this)
        rv_vehicles.adapter = adapter
    }


    private fun setOnClickListeners() {
        arrow_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // TODO: open user tab instead of 1st tab
        }

        button.setOnClickListener {
            val intent = Intent(this, VehicleAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}