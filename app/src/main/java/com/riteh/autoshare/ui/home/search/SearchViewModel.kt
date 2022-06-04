package com.riteh.autoshare.ui.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.util.Pair as APair


class SearchViewModel : ViewModel() {
    val location: MutableLiveData<String> =
        MutableLiveData<String>("Pick a location")

    val locationLatLng: MutableLiveData<LatLng> =
        MutableLiveData<LatLng>()

    private val calendarStart = Calendar.getInstance()
    val startDate: MutableLiveData<Date> = MutableLiveData<Date>(calendarStart.time)
    val endDate: MutableLiveData<Date> = MutableLiveData<Date>(getDaysFrom(7))
    val dateRange: MutableLiveData<String> = MutableLiveData<String>("Select dates")

    fun setLocation(text: String) {
        location.value = text
    }

    fun setLatLng(latLng: LatLng) {
        locationLatLng.value = latLng
    }

    fun setDates(dates: APair<Long, Long>) {
        startDate.value = Date(dates.first)
        endDate.value = Date(dates.second)

        formatSelectedDateRange(startDate.value!!, endDate.value!!)
    }

    private fun formatSelectedDateRange(startDate: Date, endDate: Date) {
        val dateFormat = SimpleDateFormat("MMM d", Locale.ENGLISH)
        dateRange.value = dateFormat.format(startDate) + " - " + dateFormat.format(endDate)
    }

    private fun getDaysFrom(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

        return calendar.time
    }
}