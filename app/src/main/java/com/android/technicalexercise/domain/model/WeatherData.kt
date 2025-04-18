package com.android.technicalexercise.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey val id: Int? = null,
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
    val country: String = "",
    val dt: Int = 0,
    val timezone: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
)
