package com.riteh.autoshare.network

import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.data.dataholders.VehicleListItem
import retrofit2.http.*
import java.util.*

interface VehicleApi {

    @FormUrlEncoded
    @POST("vehicles/create")
    suspend fun createVehicle(
        @Field("owner_id") ownerID: Int,
        @Field("brand") brand: String,
        @Field("model") model: String,
        @Field("year") year: Int,
        @Field("seat_count") seats: Int,
        @Field("door_count") doors: Int,
        @Field("licence_plate") licensePlate: String,
        @Field("registered_until") registeredUntil: Date,
        @Field("image") image: String,
        @Field("description") description: String,
        @Field("rent_cost") rentCost: Float,
        @Field("daily_distance_limit") dailyDistanceLimit: Float,
        @Field("cost_per_kilometer") costPerKilometer: Float,
        @Field("rating_avg") ratingAvg: Float
    ): Vehicle


    @GET("vehicles/{id}")
    suspend fun getVehiclesByUserId(
        @Path("id") id: Int?
    ): VehicleListItem

}