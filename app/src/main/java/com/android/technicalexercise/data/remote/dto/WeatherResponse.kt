package com.android.technicalexercise.data.remote.dto

import com.android.technicalexercise.domain.model.Clouds
import com.android.technicalexercise.domain.model.Coordinates
import com.android.technicalexercise.domain.model.Main
import com.android.technicalexercise.domain.model.Sys
import com.android.technicalexercise.domain.model.Weather
import com.android.technicalexercise.domain.model.Wind
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int,
)
