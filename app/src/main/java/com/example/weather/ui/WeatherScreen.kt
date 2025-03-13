package com.example.weather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weather.data.DailyWeather
import com.example.weather.viewmodel.WeatherState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherScreen(state: WeatherState, onRefresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRefresh) {
                        Text("重试")
                    }
                }
            }
            state.weatherData != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.weatherData.daily) { dailyWeather ->
                        WeatherCard(dailyWeather = dailyWeather)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(dailyWeather: DailyWeather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDate(dailyWeather.dt),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${dailyWeather.weather.firstOrNull()?.icon}@2x.png",
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(50.dp)
                )
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "温度: ${dailyWeather.temp.day.toInt()}°C",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "最高: ${dailyWeather.temp.max.toInt()}°C",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "最低: ${dailyWeather.temp.min.toInt()}°C",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "湿度: ${dailyWeather.humidity}%",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "风速: ${dailyWeather.wind_speed} m/s",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            Text(
                text = dailyWeather.weather.firstOrNull()?.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MM月dd日 EEEE", Locale.CHINESE)
    return sdf.format(Date(timestamp * 1000))
} 