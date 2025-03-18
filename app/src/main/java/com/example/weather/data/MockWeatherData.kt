package com.example.weather.data

object MockWeatherData {
    fun getMockWeatherResponse(): WeatherResponse {
        val currentTime = System.currentTimeMillis() / 1000
        val dailyForecasts = (0..6).map { dayOffset ->
            DailyForecast(
                dt = currentTime + dayOffset * 24 * 60 * 60,
                main = Main(
                    temp = 25.0 + dayOffset,
                    feels_like = 24.0 + dayOffset,
                    temp_min = 20.0 + dayOffset,
                    temp_max = 30.0 + dayOffset,
                    pressure = 1015,
                    sea_level = 1015,
                    grnd_level = 1012,
                    humidity = 65 + dayOffset,
                    temp_kf = 0.0
                ),
                weather = listOf(
                    Weather(
                        id = 800,
                        main = "Clear",
                        description = "晴朗",
                        icon = "01d"
                    )
                ),
                clouds = Clouds(all = 0),
                wind = Wind(
                    speed = 3.5,
                    deg = 70,
                    gust = 4.0
                ),
                visibility = 10000,
                pop = 0.0,
                sys = Sys(pod = "d"),
                dt_txt = "2024-03-17 12:00:00"
            )
        }

        return WeatherResponse(
            cod = "200",
            message = 0.0,
            cnt = 7,
            list = dailyForecasts,
            city = City(
                id = 1809858,
                name = "广州",
                coord = Coordinates(
                    lon = 113.2644,
                    lat = 23.1291
                ),
                country = "CN",
                population = 1608437,
                timezone = 28800,
                sunrise = currentTime - 3600 * 6,
                sunset = currentTime + 3600 * 6
            )
        )
    }
} 