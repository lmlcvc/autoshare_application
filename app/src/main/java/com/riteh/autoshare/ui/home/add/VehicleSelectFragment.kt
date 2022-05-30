package com.riteh.autoshare.ui.home.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.adapters.VehiclesForRentListAdapter
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.databinding.FragmentVehicleSelectBinding
import kotlinx.android.synthetic.main.fragment_vehicle_select.*


class VehicleSelectFragment : Fragment() {

    private var binding: FragmentVehicleSelectBinding? = null

    private lateinit var adapter: VehiclesForRentListAdapter
    private lateinit var vehiclesList: List<Vehicle>

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

        setUpRecyclerView(listOf())
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        iv_close.setOnClickListener {
            // TODO: cancel adding availability
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
}