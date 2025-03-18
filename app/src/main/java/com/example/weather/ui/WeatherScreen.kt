package com.example.weather.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weather.data.City
import com.example.weather.data.DailyForecast
import com.example.weather.data.WeatherResponse
import com.example.weather.viewmodel.WeatherState
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(state: WeatherState, onRefresh: () -> Unit) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.error != null -> {
                ErrorContent(
                    error = state.error,
                    onRefresh = onRefresh
                )
            }
            state.weatherData != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            CityHeader(city = state.weatherData.city)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        
                        items(
                            items = state.weatherData.list,
                            key = { it.dt }
                        ) { forecast ->
                            WeatherCard(forecast = forecast)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = state.isLoading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
            state.isLoading && state.weatherData == null -> {
                WeatherSkeletonList()
            }
        }
    }
}

@Composable
private fun CityHeader(city: City) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = city.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = city.country,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun WeatherCard(forecast: DailyForecast) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDate(forecast.dt),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            WeatherContent(
                forecast = forecast,
                expanded = expanded
            )
        }
    }
}

@Composable
private fun WeatherContent(
    forecast: DailyForecast,
    expanded: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherIcon(
                iconCode = forecast.weather.firstOrNull()?.icon ?: "",
                description = forecast.weather.firstOrNull()?.description ?: ""
            )
            
            TemperatureInfo(
                currentTemp = forecast.main.temp,
                feelsLike = forecast.main.feels_like,
                minTemp = forecast.main.temp_min,
                maxTemp = forecast.main.temp_max
            )
            
            WeatherDetails(
                humidity = forecast.main.humidity,
                windSpeed = forecast.wind.speed
            )
        }
        
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = forecast.weather.firstOrNull()?.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "点击收起详情",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun WeatherIcon(
    iconCode: String,
    description: String
) {
    val context = LocalContext.current
    val imageRequest = remember(iconCode) {
        ImageRequest.Builder(context)
            .data("https://openweathermap.org/img/wn/${iconCode}@2x.png")
            .crossfade(true)
            .build()
    }

    AsyncImage(
        model = imageRequest,
        contentDescription = description,
        modifier = Modifier.size(50.dp)
    )
}

@Composable
private fun TemperatureInfo(
    currentTemp: Double,
    feelsLike: Double,
    minTemp: Double,
    maxTemp: Double
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "温度: ${currentTemp.toInt()}°C",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "体感: ${feelsLike.toInt()}°C",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "最高: ${maxTemp.toInt()}°C",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "最低: ${minTemp.toInt()}°C",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun WeatherDetails(
    humidity: Int,
    windSpeed: Double
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "湿度: ${humidity}%",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "风速: ${windSpeed} m/s",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MM月dd日 EEEE", Locale.CHINESE)
    return sdf.format(Date(timestamp * 1000))
}

@Composable
private fun ErrorContent(
    error: String,
    onRefresh: () -> Unit
) {
    var showError by remember { mutableStateOf(true) }
    
    AnimatedVisibility(
        visible = showError,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "错误",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    showError = false
                    onRefresh()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "重试",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("重试")
            }
        }
    }
}

@Composable
private fun WeatherSkeletonList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(7) { // 显示7个骨架项
            WeatherSkeletonItem()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun WeatherSkeletonItem() {
    val shimmerAnim = rememberInfiniteTransition()
    val alpha by shimmerAnim.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

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
            // 日期骨架
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .graphicsLayer { this.alpha = alpha }
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 图标骨架
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .graphicsLayer { this.alpha = alpha }
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                
                // 温度信息骨架
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .graphicsLayer { this.alpha = alpha }
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                
                // 详情骨架
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .graphicsLayer { this.alpha = alpha }
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
} 