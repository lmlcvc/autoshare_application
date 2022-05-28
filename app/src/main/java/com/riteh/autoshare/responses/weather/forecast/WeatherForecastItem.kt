package com.riteh.autoshare.responses.weather.forecast

import java.io.Serializable

data class WeatherForecastItem(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int): Serializable {

    var quantity = 0
    }