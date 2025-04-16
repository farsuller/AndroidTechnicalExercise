package com.android.technicalexercise.domain.usecase

import com.android.technicalexercise.data.remote.dto.WeatherResponse
import com.android.technicalexercise.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetWeather(
    private val repository: WeatherRepository
) {
    operator fun invoke(latitude: Double, longitude: Double): Flow<Response<WeatherResponse>> =
        repository.getWeather(latitude = latitude, longitude = longitude)
}