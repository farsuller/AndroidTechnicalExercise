package com.android.technicalexercise.data.repository

import com.android.technicalexercise.domain.model.WeatherData
import com.android.technicalexercise.domain.repository.WeatherHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherHistoryRepositoryImpl : WeatherHistoryRepository {

    private val weatherDataList = mutableListOf<WeatherData>()

    override fun getWeatherHistory(): Flow<List<WeatherData>> = flow { emit(weatherDataList) }

    override suspend fun insertWeatherHistory(weatherData: WeatherData) {
        weatherDataList.add(weatherData)
    }
}
