package com.riteh.autoshare.data.dataholders

data class Vehicle(
    var brand: String = "",
    val cost_per_kilometer: String = "",
    val daily_distance_limit: String = "",
    var image: String = "",
    var licence_plate: String = "",
    var model: String = "",
    var owner_id: Int = -1,
    val rating_avg: String = "",
    var registered_until: String = "",
    val rent_cost: String = "",
    val vehicle_id: Int = -1,
    var year: Int = -1,
    var seats: Int = 0,
    var doors: Int = 0,
    var description: String = ""
)
