package com.android.technicalexercise.presentation.screen.home

import com.android.technicalexercise.domain.model.WeatherData

data class WeatherHistoryState(
    val weatherHistory: List<WeatherData> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)
