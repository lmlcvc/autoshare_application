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
        vehicle.value?.licensePlate = licensePlate

        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
        vehicle.value?.registeredUntil = formatter.parse(registeredUntil)!!

        vehicle.value?.image = image
        vehicle.value?.description = description
    }

    fun setOwnerID(id: Int) {
        vehicle.value?.ownerID = id
    }

    suspend fun createVehicle() {
        api.createVehicle(
            vehicle.value?.ownerID!!,
            vehicle.value?.brand!!,
            vehicle.value?.model!!,
            vehicle.value?.year!!,
            vehicle.value?.seats!!,
            vehicle.value?.doors!!,
            vehicle.value?.licensePlate!!,
            vehicle.value?.registeredUntil!!,
            vehicle.value?.image!!,
            vehicle.value?.description!!,
            vehicle.value?.rentCost!!,
            vehicle.value?.dailyDistanceLimit!!,
            vehicle.value?.costPerKilometer!!,
            vehicle.value?.ratingAvg!!
        )
    }

}