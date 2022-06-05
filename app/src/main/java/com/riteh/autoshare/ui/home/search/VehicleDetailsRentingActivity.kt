package com.riteh.autoshare.ui.home.search

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.bumptech.glide.Glide
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

        if(!vehicle.image.isNullOrEmpty()) {
            car_photos.setImageBitmap(getBitmapFromBase64(vehicle.image))
        }
    }

    private fun getBitmapFromBase64(encodedImage: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}