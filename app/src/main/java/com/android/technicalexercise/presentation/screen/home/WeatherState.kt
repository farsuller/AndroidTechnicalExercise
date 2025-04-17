package com.android.technicalexercise.presentation.screen.home

data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

data class WeatherData(
    val cityName: String = "",
    val temperature: Double = 0.0,
    val feelsLike: Double = 0.0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val windDeg: Int = 0,
    val weatherMain: String? = null,
    val weatherDescription: String? = null,
    val cloudiness: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val timezone: Int = 0,
    val country: String = "",
    val dt: Int = 0,
    val timeZone: Int = 0,
)
