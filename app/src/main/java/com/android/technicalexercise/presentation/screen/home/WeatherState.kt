package com.android.technicalexercise.presentation.screen.home

import com.android.technicalexercise.domain.model.WeatherData

data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)
