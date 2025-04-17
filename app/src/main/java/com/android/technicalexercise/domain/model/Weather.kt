package com.android.technicalexercise.domain.model

import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String,
)

data class Coordinates(
    val lat: Double,
    val lon: Double,
)

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double,
)

data class Clouds(
    val all: Int,
)

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int,
)

data class Main(

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("grnd_level")
    val groundLevel: Int,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("sea_level")
    val seaLevel: Int,

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    @SerializedName("temp_min")
    val tempMin: Double,
)
