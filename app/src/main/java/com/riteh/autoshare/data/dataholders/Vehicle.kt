package com.riteh.autoshare.data.dataholders

import java.util.*

data class Vehicle(
    var ownerID: Int = 0,
    var brand: String = "",
    var model: String = "",
    var seats: Int = 0,
    var doors: Int = 0,
    var year: Int = 1900,
    var licensePlate: String = "",
    var registeredUntil: Date = Date(),
    var image: String = "",
    var description: String = "",
    var rentCost: Float = 0.toFloat(),
    var dailyDistanceLimit: Float = 0.toFloat(),
    var costPerKilometer: Float = 0.toFloat(),
    var ratingAvg: Float = 0.toFloat()
)
