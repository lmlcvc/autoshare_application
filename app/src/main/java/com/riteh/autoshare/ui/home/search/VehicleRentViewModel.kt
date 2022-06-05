package com.riteh.autoshare.ui.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riteh.autoshare.data.dataholders.Vehicle

class VehicleRentViewModel : ViewModel()  {
    val vehicle: MutableLiveData<Vehicle> =
        MutableLiveData<Vehicle>(Vehicle())

    fun setVehicle(v: Vehicle){
        vehicle.value = v
    }
}