package com.riteh.autoshare.ui.home.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel : ViewModel() {
    val ownerID: MutableLiveData<Int> =
        MutableLiveData<Int>(-1)

    val dailyPrice: MutableLiveData<Double> =
        MutableLiveData<Double>(-1.0)

    val distanceLimit: MutableLiveData<Double> =
        MutableLiveData<Double>(-1.0)

    val extraPrice: MutableLiveData<Double> =
        MutableLiveData<Double>(-1.0)

    val location: MutableLiveData<String> =
        MutableLiveData<String>("")

    val locationLatLng: MutableLiveData<LatLng> =
        MutableLiveData<LatLng>(LatLng(45.37, 14.35))

    val startDate: MutableLiveData<Date> = MutableLiveData<Date>()
    val endDate: MutableLiveData<Date> = MutableLiveData<Date>()

    val startDateString: MutableLiveData<String> =
        MutableLiveData<String>("Start date")

    val endDateString: MutableLiveData<String> =
        MutableLiveData<String>("End date")


    fun setOwnerID(id: Int) {
        ownerID.value = id
    }

    fun setLocation(loc: String) {
        location.value = loc
    }

    fun setLocationLatLng(loc: LatLng) {
        locationLatLng.value = loc
    }

    fun setStartDate(date: Long) {
        startDate.value = Date(date)
        startDateString.value = formatDate(startDate.value!!)
    }

    fun setEndDate(date: Long) {
        endDate.value = Date(date)
        endDateString.value = formatDate(endDate.value!!)
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("MMM d", Locale.ENGLISH)
        return dateFormat.format(date)
    }
}