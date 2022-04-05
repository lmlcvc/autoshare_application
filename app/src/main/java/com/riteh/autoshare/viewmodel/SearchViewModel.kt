package com.riteh.autoshare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SearchViewModel : ViewModel() {
    val location: MutableLiveData<String> =
        MutableLiveData<String>("Pick a location")

    fun setLocation(text: String) {
        location.value = text
    }
}