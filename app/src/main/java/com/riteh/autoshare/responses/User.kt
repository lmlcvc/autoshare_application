package com.riteh.autoshare.responses

import java.util.*

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val surname: String,
    val date_of_birth: Date,
    val license_id: String,
    val renter_avg_rating: Double,
    val rentee_avg_rating: Double
)
