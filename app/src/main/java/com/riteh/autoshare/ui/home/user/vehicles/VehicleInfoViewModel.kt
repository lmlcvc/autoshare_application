package com.riteh.autoshare.ui.home.user.vehicles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.network.RemoteDataSource
import com.riteh.autoshare.network.VehicleApi
import java.text.SimpleDateFormat
import java.util.*


class VehicleInfoViewModel : ViewModel() {

    private val api = RemoteDataSource().buildApi(VehicleApi::class.java)

    val vehicle: MutableLiveData<Vehicle> =
        MutableLiveData<Vehicle>(Vehicle())


    fun setBrand(text: String) {
        vehicle.value?.brand = text
    }

    fun setModel(text: String) {
        vehicle.value?.model = text
    }

    fun setDetails(
        seats: String,
        doors: String,
        year: String,
        licensePlate: String,
        registeredUntil: String,
        image: String,
        description: String
    ) {
        vehicle.value?.seats = seats.toInt()
        vehicle.value?.doors = doors.toInt()
        vehicle.value?.year = year.toInt()
        vehicle.value?.licence_plate = licensePlate

        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
        vehicle.value?.registered_until = formatter.parse(registeredUntil).toString()

        vehicle.value?.image = image
        vehicle.value?.description = description
    }

    fun setOwnerID(id: Int) {
        vehicle.value?.owner_id = id
    }

    suspend fun createVehicle() {
        api.createVehicle(
            vehicle.value?.owner_id!!,
            vehicle.value?.brand!!,
            vehicle.value?.model!!,
            vehicle.value?.year!!,
            vehicle.value?.seats!!,
            vehicle.value?.doors!!,
            vehicle.value?.licence_plate!!,
            vehicle.value?.registered_until!!,
            vehicle.value?.image!!,
            vehicle.value?.description!!,
            vehicle.value?.rent_cost!!,
            vehicle.value?.daily_distance_limit!!,
            vehicle.value?.cost_per_kilometer!!,
            vehicle.value?.rating_avg!!
        )
    }

}