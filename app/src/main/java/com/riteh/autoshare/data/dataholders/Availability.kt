package com.riteh.autoshare.data.dataholders

data class Availability(
    val id: Int,
    val vehicle_id: Int,
    val start_time: String,
    val end_time: String,
    val latitude: Double,
    val longitude: Double
)
