package com.example.weather.viewmodel

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

    fun getWeatherForecast(lat: Double = 31.2304, lon: Double = 121.4737) { // 默认上海的坐标
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                // 如果API密钥是默认值，使用模拟数据
                if (WeatherApi.API_KEY == "YOUR_API_KEY") {
                    state = state.copy(
                        weatherData = MockWeatherData.getMockWeatherResponse(),
                        error = null,
                        isLoading = false
                    )
                } else {
                    // 使用真实API
                    val weatherResponse = weatherApi.getWeatherForecast(
                        lat = lat,
                        lon = lon,
                        apiKey = WeatherApi.API_KEY
                    )
                    state = state.copy(
                        weatherData = weatherResponse,
                        error = null,
                        isLoading = false
                    )
                }
            } catch (e: IOException) {
                state = state.copy(
                    error = "网络连接失败，请检查网络设置",
                    isLoading = false
                )
            } catch (e: Exception) {
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