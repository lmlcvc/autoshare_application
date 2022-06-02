package com.riteh.autoshare.network

import com.riteh.autoshare.data.dataholders.Availability
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
        @Field("registered_until") registeredUntil: String,
        @Field("image") image: String,
        @Field("description") description: String,
        @Field("rent_cost") rentCost: String,
        @Field("daily_distance_limit") dailyDistanceLimit: String,
        @Field("cost_per_kilometer") costPerKilometer: String,
        @Field("rating_avg") ratingAvg: String
    ): Vehicle


    @FormUrlEncoded
    @POST("availabilities")
    suspend fun createAvailability(
        @Field("vehicle_id") ownerID: Int,
        @Field("start_date") brand: Date,
        @Field("end_date") model: Date,
        @Field("latitude") year: Double,
        @Field("longitude") seats: Double
    ): Availability


    // TODO: I/okhttp.OkHttpClient: <-- 500 Internal Server Error http://46.101.208.185/api/vehicles/update/%7Bvehicle_id%7D (295ms)
    @FormUrlEncoded
    @POST("vehicles/update/{vehicle_id}")
    suspend fun addVehicleRentInfo(
        @Field("rent_cost") rent_cost: Double,
        @Field("daily_distance_limit") daily_distance_limit: Double,
        @Field("cost_per_kilometer") cost_per_kilometer: Double
    ): VehicleListItem


    @GET("vehicles/user/{id}")
    suspend fun getVehiclesByUserId(
        @Path("id") id: Int?
    ): VehicleListItem

}