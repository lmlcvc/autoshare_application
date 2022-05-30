package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.fragment_vehicle_adding_completed.*


class VehicleAddingCompletedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle_adding_completed, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_vehicleAddingCompletedFragment_to_vehicleExtraInfoActivity)
        }

        tv_continue.setOnClickListener {
            findNavController().navigate(R.id.action_vehicleAddingCompletedFragment_to_vehicleListActivity)
        }
    }
}