package com.android.technicalexercise.domain

import com.android.technicalexercise.data.repository.FakeWeatherHistoryRepositoryImpl
import com.android.technicalexercise.domain.model.WeatherData
import com.android.technicalexercise.domain.usecase.AddWeather
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddWeatherTest {

    private lateinit var addWeather: AddWeather
    private lateinit var fakeWeatherHistoryRepository: FakeWeatherHistoryRepositoryImpl

    @Before
    fun setUp() {
        fakeWeatherHistoryRepository = FakeWeatherHistoryRepositoryImpl()
        addWeather = AddWeather(fakeWeatherHistoryRepository)
    }

    @Test
    fun `Add Weather, weather is added`() = runBlocking {
        val weather = WeatherData(
            cityName = "San Mateo",
            country = "PH",
            temperature = 307.53,
            feelsLike = 314.53,
            weatherMain = "Clouds",
            weatherDescription = "scattered clouds",
            humidity = 60,
            windSpeed = 3.13,
            dt = 1745214087,
            sunrise = 1745185082,
            sunset = 1745230214,
            timezone = 28800,
        )

        addWeather.invoke(weather)

        val weatherList = fakeWeatherHistoryRepository.getWeatherHistory().first()
        assertThat(weatherList).contains(weather)
    }
}
