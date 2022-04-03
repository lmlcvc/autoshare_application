package com.riteh.autoshare.viewmodel

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riteh.autoshare.R

class SearchViewModel : ViewModel() {
    val location: MutableLiveData<String> =
        MutableLiveData<String>("Pick a location")


    fun setLocation(text: String) {
        location.value = text
    }
}