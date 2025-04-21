package com.android.technicalexercise.domain

import com.android.technicalexercise.data.repository.FakeWeatherHistoryRepositoryImpl
import com.android.technicalexercise.domain.model.WeatherData
import com.android.technicalexercise.domain.usecase.GetWeathers
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class GetWeatherTest {

    private lateinit var getWeather: GetWeathers
    private lateinit var fakeWeatherHistoryRepository: FakeWeatherHistoryRepositoryImpl

    private lateinit var weather1: WeatherData
    private lateinit var weather2: WeatherData

    @Before
    fun setUp() {
        fakeWeatherHistoryRepository = FakeWeatherHistoryRepositoryImpl()
        getWeather = GetWeathers(fakeWeatherHistoryRepository)

        runBlocking {
            weather1 = WeatherData(
                id = 1,
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

            weather2 = WeatherData(
                id = 2,
                cityName = "Quezon City",
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

            fakeWeatherHistoryRepository.insertWeatherHistory(weather1)
            fakeWeatherHistoryRepository.insertWeatherHistory(weather2)
        }
    }

    @Test
    fun `Get weather first, returns weather first`() = runBlocking {
        val weatherList = getWeather().first()
        val firstWeather = weatherList.firstOrNull()

        assertThat(firstWeather).isEqualTo(weather1)
    }

    @Test
    fun `Get weathers, returns weather history list`() = runTest {
        val weatherList = getWeather().first()
        assertThat(weatherList).containsExactly(weather1, weather2)
    }
}
