package com.example.weather.api

import android.util.Log
import com.example.weather.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") count: Int = 7,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        /**
         * OpenWeatherMap API密钥
         * 获取步骤：
         * 1. 访问 https://openweathermap.org/api
         * 2. 注册账号
         * 3. 登录后访问 https://home.openweathermap.org/api_keys
         * 4. 复制API密钥到这里
         * 注意：新注册的API密钥可能需要等待几个小时才能激活
         */
        const val API_KEY = "YOUR_API_KEY" // 替换为你的API密钥
    }
} 