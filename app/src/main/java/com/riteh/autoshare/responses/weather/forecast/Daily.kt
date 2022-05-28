package com.riteh.autoshare.responses.weather.forecast

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("dew_point") val dew_point: Double,
    @SerializedName("dt") val dt: Int,
    @SerializedName("feels_like") val feels_like: FeelsLike,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("moon_phase") val moon_phase: Double,
    @SerializedName("moonrise") val moonrise: Int,
    @SerializedName("moonset") val moonset: Int,
    @SerializedName("pop") val pop: Float,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int,
    @SerializedName("temp") val temp: Temp,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("weather") val weather: List<WeatherX>,
    @SerializedName("wind_deg") val wind_deg: Int,
    @SerializedName("wind_gust") val wind_gust: Double,
    @SerializedName("wind_speed") val wind_speed: Double
)