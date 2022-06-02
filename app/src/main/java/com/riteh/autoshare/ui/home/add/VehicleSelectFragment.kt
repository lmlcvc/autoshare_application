package com.riteh.autoshare.ui.home.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.VehiclesForRentListAdapter
import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.databinding.FragmentVehicleSelectBinding
import com.riteh.autoshare.network.VehicleApi
import kotlinx.android.synthetic.main.fragment_vehicle_select.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class VehicleSelectFragment : Fragment() {

    private var binding: FragmentVehicleSelectBinding? = null

    private lateinit var adapter: VehiclesForRentListAdapter
    private var vehiclesList = mutableListOf<Vehicle>()

    private val sharedViewModel: AddViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentVehicleSelectBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.vehicleSelectFragment = this

        makeAPIRequestWithOwnerID()
        // makeAPIRequest()
        setOnClickListeners()
    }

    private fun makeAPIRequestWithOwnerID() {
        val userPreferences = UserPreferences(requireContext())

        GlobalScope.launch(
            Dispatchers.IO
        ) {
            userPreferences.getUserFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    sharedViewModel.setOwnerID(it.id)
                    makeAPIRequest(it.id)
                }
            }
        }
    }


    private fun makeAPIRequest(id: Int) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val api: VehicleApi = Retrofit.Builder()
            .baseUrl(getString(R.string.AUTOSHARE_API))
            .client(client)
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
    private fun setUpRecyclerView(vehicles: List<Vehicle>) {
        rv_vehicles.layoutManager = GridLayoutManager(requireContext(), 1)

        adapter = VehiclesForRentListAdapter(vehicles, requireContext(), sharedViewModel)
        rv_vehicles.adapter = adapter
    }


    private fun setOnClickListeners() {
        iv_close.setOnClickListener {
            requireActivity().finish()
        }
    }
}