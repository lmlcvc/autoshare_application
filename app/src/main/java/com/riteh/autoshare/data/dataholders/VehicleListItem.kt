package com.riteh.autoshare.data.dataholders

import com.google.gson.annotations.SerializedName


data class VehicleListItem(
    @SerializedName("data") val data: List<Vehicle>
)
