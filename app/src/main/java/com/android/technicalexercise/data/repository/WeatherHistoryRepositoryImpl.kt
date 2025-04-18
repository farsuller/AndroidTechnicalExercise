package com.android.technicalexercise.data.repository

import com.android.technicalexercise.data.local.WeatherDao
import com.android.technicalexercise.domain.model.WeatherData
import com.android.technicalexercise.domain.repository.WeatherHistoryRepository
import kotlinx.coroutines.flow.Flow

class WeatherHistoryRepositoryImpl(
    private val dao: WeatherDao,
) : WeatherHistoryRepository {

    override fun getWeatherHistory(): Flow<List<WeatherData>> = dao.getWeatherHistory()

    override suspend fun insertWeatherHistory(weatherData: WeatherData) =
        dao.insertWeather(weatherData = weatherData)
}
