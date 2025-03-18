package com.example.weather.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.api.WeatherApi
import com.example.weather.data.MockWeatherData
import com.example.weather.data.WeatherResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class WeatherViewModel : ViewModel() {
    var state by mutableStateOf(WeatherState())
        private set

    private val weatherApi = Retrofit.Builder()
        .baseUrl(WeatherApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    init {
        getWeatherForecast()
    }

    fun getWeatherForecast(lat: Double = 23.1291, lon: Double = 113.2644) { // 默认广州的坐标
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                Log.d("WeatherViewModel", "Using API Key: ${WeatherApi.API_KEY}")
                // 如果API密钥是默认值，使用模拟数据
                if (WeatherApi.API_KEY == "YOUR_API_KEY") {
                    Log.d("WeatherViewModel", "Using mock data because API key is default")
                    state = state.copy(
                        weatherData = MockWeatherData.getMockWeatherResponse(),
                        error = null,
                        isLoading = false
                    )
                } else {
                    // 构建完整的请求URL用于调试
                    val requestUrl = "${WeatherApi.BASE_URL}data/2.5/forecast/daily?lat=$lat&lon=$lon&cnt=7&units=metric&appid=${WeatherApi.API_KEY}"
                    Log.d("WeatherViewModel", "Making API call to: $requestUrl")
                    
                    // 使用真实API
                    val weatherResponse = weatherApi.getWeatherForecast(
                        lat = lat,
                        lon = lon,
                        count = 7,
                        apiKey = WeatherApi.API_KEY
                    )
                    Log.d("WeatherViewModel", "API call successful")
                    state = state.copy(
                        weatherData = weatherResponse,
                        error = null,
                        isLoading = false
                    )
                }
            } catch (e: IOException) {
                Log.e("WeatherViewModel", "Network error", e)
                state = state.copy(
                    error = "网络连接失败，请检查网络设置",
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "API error: ${e.message}", e)
                // 如果API调用失败，使用模拟数据
                state = state.copy(
                    weatherData = MockWeatherData.getMockWeatherResponse(),
                    error = null,
                    isLoading = false
                )
            }
        }
    }
}

data class WeatherState(
    val weatherData: WeatherResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 