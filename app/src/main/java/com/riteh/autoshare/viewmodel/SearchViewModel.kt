package com.riteh.autoshare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.core.util.Pair as APair
import java.util.*


class SearchViewModel : ViewModel() {
    val location: MutableLiveData<String> =
        MutableLiveData<String>("Pick a location")

    private val calendarStart = Calendar.getInstance()
    val startDate: MutableLiveData<Date> = MutableLiveData<Date>(calendarStart.time)
    val endDate: MutableLiveData<Date> = MutableLiveData<Date>(getDaysFrom(7))

    fun setLocation(text: String) {
        location.value = text
    }

    fun setDates(dates: APair<Long, Long>) {
        startDate.value = Date(dates.first)
        endDate.value = Date(dates.second)
    }

    private fun getDaysFrom(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

        return calendar.time
    }
}