package com.riteh.autoshare.data.api

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
    //weather?lat="+latitude+"&lon="+longitude+"&appid="+apiKey
    //@PATCH("weather?lat={lat}&lon={lon}&appid={appid}")
    //@GET("weather?")
    //@GET("weather?lat=-6.2087634&lon=106.845599&appid=72a2a76b30dd2c83d3e9ca25905faa9c")
    @GET("weather")
     fun getCurrentWeather(@Query("lat") lat: String?,
                           @Query("lon") lon: String?,
                           @Query("appid") appid: String?): Call<WeatherForecastItem>
}


