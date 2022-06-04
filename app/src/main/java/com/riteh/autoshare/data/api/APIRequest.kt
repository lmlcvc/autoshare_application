package com.riteh.autoshare.data.api

import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.data.dataholders.VehicleListItem
import com.riteh.autoshare.responses.weather.current.WeatherCurrentItem
import com.riteh.autoshare.responses.weather.forecast.Current
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate
import java.util.*


interface APIRequest {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appid: String?,
        @Query("units") units: String?
    ): Call<WeatherCurrentItem>

    @GET("onecall")
    fun getCurrentWeatherWeek(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appid: String?,
        @Query("units") units: String?,
    ): Call<WeatherForecastItem>

    @GET("vehicles/search")
    suspend fun getCarList(
        @Query("start_date") startDate: Date,
        @Query("end_date") endDate: Date,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Double
    ): VehicleListItem
}


