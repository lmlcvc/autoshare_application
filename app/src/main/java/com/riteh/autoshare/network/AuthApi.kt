package com.riteh.autoshare.network

import com.riteh.autoshare.responses.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : AuthResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun userSignUp(
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse
}