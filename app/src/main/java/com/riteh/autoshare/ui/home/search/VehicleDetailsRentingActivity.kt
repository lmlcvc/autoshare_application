package com.riteh.autoshare.ui.home.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.riteh.autoshare.R
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.ui.home.MainActivity
import com.riteh.autoshare.ui.home.user.vehicles.VehicleInfoViewModel
import kotlinx.android.synthetic.main.fragment_rent_completed.*
import kotlinx.android.synthetic.main.vehicle_details_renting.*

class VehicleDetailsRentingActivity : AppCompatActivity() {
    private lateinit var viewModel: VehicleRentViewModel
    private lateinit var vehicle: Vehicle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehicle_details_renting)

        viewModel = vita.with(VitaOwner.Multiple(this)).getViewModel()
        vehicle = viewModel.vehicle.value!!

        displayDetails()
        btn_continue.setOnClickListener {
            setContentView(R.layout.fragment_rent_completed)

            button.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    fun displayDetails() {
        name_car.text = vehicle.brand + " " + vehicle.model
        tv_title.text = vehicle.brand + " " + vehicle.model
        car_year.text = vehicle.year.toString()
        seats.text = vehicle.seats.toString()
        doors.text = vehicle.doors.toString()
        price.text = vehicle.rent_cost
    }
}