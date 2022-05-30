package com.riteh.autoshare.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*


interface UserApi {
    @FormUrlEncoded
    @POST("users/{user_id}")
    suspend fun userUpdate(
        @Path("user_id") id: Int,
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("email") email: String,
        @Field("license_id") license_id: String,
        @Field("date_of_birth") date_of_birth: Date
    )
}
