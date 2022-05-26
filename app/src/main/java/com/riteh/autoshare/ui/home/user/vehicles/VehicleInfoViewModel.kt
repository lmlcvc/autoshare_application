package com.riteh.autoshare.ui.home.user.vehicles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class VehicleInfoViewModel : ViewModel() {
    val brand: MutableLiveData<String> =
        MutableLiveData<String>("")

    val model: MutableLiveData<String> =
        MutableLiveData<String>("")

    val date: MutableLiveData<String> =
        MutableLiveData<String>("")


    fun setBrand(text: String) {
        brand.value = text
    }

    fun setModel(text: String) {
        model.value = text
    }
}