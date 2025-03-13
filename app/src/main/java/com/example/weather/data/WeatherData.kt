package com.example.weather.data

data class WeatherResponse(
    val daily: List<DailyWeather>
)

data class DailyWeather(
    val dt: Long,
    val temp: Temperature,
    val humidity: Int,
    val weather: List<Weather>,
    val wind_speed: Double
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double
)

data class Weather(
    val description: String,
    val icon: String
) 