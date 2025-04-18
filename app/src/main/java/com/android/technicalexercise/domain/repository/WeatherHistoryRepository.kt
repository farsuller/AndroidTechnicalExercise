package com.android.technicalexercise.domain.repository

import com.android.technicalexercise.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherHistoryRepository {

    fun getWeatherHistory(): Flow<List<WeatherData>>
    suspend fun insertWeatherHistory(weatherData: WeatherData)
}
