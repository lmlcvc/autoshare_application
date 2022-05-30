package com.riteh.autoshare.data.api

import com.riteh.autoshare.responses.weather.current.WeatherCurrentItem
import com.riteh.autoshare.responses.weather.forecast.Current
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query


interface APIRequest {
    @GET("weather")
     fun getCurrentWeather(@Query("lat") lat: String?,
                           @Query("lon") lon: String?,
                           @Query("appid") appid: String?,
                            @Query("units") units: String?): Call<WeatherCurrentItem>

    @GET("onecall")
      fun getCurrentWeatherWeek(@Query("lat") lat: String?,
                          @Query("lon") lon: String?,
                          @Query("appid") appid: String?,
                          @Query("units") units: String?,): Call<WeatherForecastItem>
}


