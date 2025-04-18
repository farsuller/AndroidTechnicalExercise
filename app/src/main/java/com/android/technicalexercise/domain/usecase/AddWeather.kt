package com.android.technicalexercise.domain.usecase

import com.android.technicalexercise.domain.model.WeatherData
import com.android.technicalexercise.domain.repository.WeatherHistoryRepository

class AddWeather(
    private val repository: WeatherHistoryRepository,
) {
    suspend operator fun invoke(weatherData: WeatherData) =
        repository.insertWeatherHistory(weatherData)
}
