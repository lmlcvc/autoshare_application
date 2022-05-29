package com.riteh.autoshare.ui.home.user.vehicles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riteh.autoshare.data.dataholders.Vehicle


class VehicleInfoViewModel : ViewModel() {

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
        vehicle.value?.seats = seats
        vehicle.value?.doors = doors
        vehicle.value?.year = year
        vehicle.value?.licensePlate = licensePlate
        vehicle.value?.registeredUntil = registeredUntil
        vehicle.value?.image = image
        vehicle.value?.seats = description
    }

}