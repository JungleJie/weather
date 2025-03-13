package com.example.weather.data

object MockWeatherData {
    fun getMockWeatherResponse(): WeatherResponse {
        val dailyWeatherList = listOf(
            DailyWeather(
                dt = System.currentTimeMillis() / 1000, // 今天
                temp = Temperature(
                    day = 23.5,
                    min = 18.0,
                    max = 26.0
                ),
                humidity = 65,
                weather = listOf(
                    Weather(
                        description = "晴朗",
                        icon = "01d"
                    )
                ),
                wind_speed = 3.5
            ),
            DailyWeather(
                dt = System.currentTimeMillis() / 1000 + 24 * 60 * 60, // 明天
                temp = Temperature(
                    day = 22.0,
                    min = 17.0,
                    max = 24.0
                ),
                humidity = 70,
                weather = listOf(
                    Weather(
                        description = "多云",
                        icon = "02d"
                    )
                ),
                wind_speed = 4.0
            ),
            DailyWeather(
                dt = System.currentTimeMillis() / 1000 + 2 * 24 * 60 * 60, // 后天
                temp = Temperature(
                    day = 20.0,
                    min = 16.0,
                    max = 22.0
                ),
                humidity = 75,
                weather = listOf(
                    Weather(
                        description = "小雨",
                        icon = "10d"
                    )
                ),
                wind_speed = 5.0
            ),
            DailyWeather(
                dt = System.currentTimeMillis() / 1000 + 3 * 24 * 60 * 60,
                temp = Temperature(
                    day = 21.0,
                    min = 17.0,
                    max = 23.0
                ),
                humidity = 68,
                weather = listOf(
                    Weather(
                        description = "阴天",
                        icon = "04d"
                    )
                ),
                wind_speed = 3.8
            ),
            DailyWeather(
                dt = System.currentTimeMillis() / 1000 + 4 * 24 * 60 * 60,
                temp = Temperature(
                    day = 24.0,
                    min = 19.0,
                    max = 26.0
                ),
                humidity = 62,
                weather = listOf(
                    Weather(
                        description = "晴朗",
                        icon = "01d"
                    )
                ),
                wind_speed = 3.2
            ),
            DailyWeather(
                dt = System.currentTimeMillis() / 1000 + 5 * 24 * 60 * 60,
                temp = Temperature(
                    day = 25.0,
                    min = 20.0,
                    max = 27.0
                ),
                humidity = 60,
                weather = listOf(
                    Weather(
                        description = "晴朗",
                        icon = "01d"
                    )
                ),
                wind_speed = 3.0
            ),
            DailyWeather(
                dt = System.currentTimeMillis() / 1000 + 6 * 24 * 60 * 60,
                temp = Temperature(
                    day = 23.0,
                    min = 18.0,
                    max = 25.0
                ),
                humidity = 72,
                weather = listOf(
                    Weather(
                        description = "多云",
                        icon = "02d"
                    )
                ),
                wind_speed = 4.2
            )
        )

        return WeatherResponse(daily = dailyWeatherList)
    }
} 