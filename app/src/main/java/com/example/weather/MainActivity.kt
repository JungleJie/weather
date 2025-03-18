package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.weather.ui.LoginScreen
import com.example.weather.ui.WeatherScreen
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showWeather by remember { mutableStateOf(false) }

                    if (showWeather) {
                        WeatherScreen(
                            state = viewModel.state,
                            onRefresh = { viewModel.getWeatherForecast() }
                        )
                    } else {
                        LoginScreen(
                            onNavigateToWeather = {
                                showWeather = true
                                viewModel.getWeatherForecast()
                            }
                        )
                    }
                }
            }
        }
    }
}