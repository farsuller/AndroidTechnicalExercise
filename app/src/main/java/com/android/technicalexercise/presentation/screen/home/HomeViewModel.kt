package com.android.technicalexercise.presentation.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.domain.usecase.WeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (
    private val weatherUseCase: WeatherUseCase
): ViewModel() {

    private val _location = mutableStateOf<Location?>(null)
    val location : State<Location?> = _location

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun updateLocation (location: Location){
        _location.value = location
    }

    fun getWeatherData(latitude: Double, longitude: Double) = viewModelScope.launch {
        weatherUseCase.getWeather(latitude = latitude, longitude = longitude)
            .onStart {
                _weatherState.value = WeatherState(isLoading = true)
            }
            .catch { e ->
                _weatherState.value = WeatherState(errorMessage = e.message)
            }
            .collect { result ->
                result.body()?.let { weatherResponse ->
                    _weatherState.update {
                        it.copy(
                            cityName = weatherResponse.name,
                            temperature = weatherResponse.main.temp,
                            feelsLike = weatherResponse.main.feelsLike,
                            tempMin = weatherResponse.main.tempMin,
                            tempMax = weatherResponse.main.tempMax,
                            pressure = weatherResponse.main.pressure,
                            humidity = weatherResponse.main.humidity,
                            windSpeed = weatherResponse.wind.speed,
                            windDeg = weatherResponse.wind.deg,
                            weatherMain = weatherResponse.weather.firstOrNull()?.main,
                            weatherDescription = weatherResponse.weather.firstOrNull()?.description,
                            cloudiness = weatherResponse.clouds.all,
                            sunrise = weatherResponse.sys.sunrise,
                            sunset = weatherResponse.sys.sunset,
                            timezone = weatherResponse.timezone,
                            country = weatherResponse.sys.country,
                            dt = weatherResponse.dt,
                            timeZone = weatherResponse.timezone,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
    }

}