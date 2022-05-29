package com.riteh.autoshare.responses.weather.forecast

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("dew_point") val dew_point: Double,
    @SerializedName("dt") val dt: Long,
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("temp") val temp: Double,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind_deg") val wind_deg: Int,
    @SerializedName("wind_speed") val wind_speed: Double
)