package com.android.technicalexercise.domain.repository

import com.android.technicalexercise.data.remote.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface WeatherRepository {
    fun getWeather(latitude: Double, longitude: Double): Flow<Response<WeatherResponse>>
}
