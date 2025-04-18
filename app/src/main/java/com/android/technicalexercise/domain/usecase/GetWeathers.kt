package com.android.technicalexercise.domain.usecase

import com.android.technicalexercise.domain.repository.WeatherHistoryRepository

class GetWeathers(
    private val repository: WeatherHistoryRepository,
) {
    operator fun invoke() = repository.getWeatherHistory()
}
