package com.android.technicalexercise.data.repository

import com.android.technicalexercise.BuildConfig
import com.android.technicalexercise.data.remote.WeatherApi
import com.android.technicalexercise.data.remote.dto.WeatherResponse
import com.android.technicalexercise.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override fun getWeather(latitude: Double, longitude: Double): Flow<Response<WeatherResponse>> = flow {
        try {
            val response = weatherApi.getWeather(latitude = latitude, longitude = longitude, apiKey = BuildConfig.API_KEY)
            emit(response)
        }
        catch (e : Exception){
            val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
            emit(Response.error(500, errorResponseBody))
        }
    }.flowOn(Dispatchers.IO)
}