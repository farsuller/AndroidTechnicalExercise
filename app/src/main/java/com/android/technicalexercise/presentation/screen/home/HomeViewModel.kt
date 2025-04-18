package com.android.technicalexercise.presentation.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.domain.model.WeatherData
import com.android.technicalexercise.domain.usecase.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherUseCase: WeatherUseCase,
) : ViewModel() {

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _weatherHistoryState = MutableStateFlow(WeatherHistoryState())
    val weatherHistoryState: StateFlow<WeatherHistoryState> = _weatherHistoryState.asStateFlow()

    fun updateLocation(location: Location) {
        _location.value = location
    }

    fun getWeatherData(latitude: Double, longitude: Double) = viewModelScope.launch {
        weatherUseCase.getWeather(latitude = latitude, longitude = longitude)
            .onStart {
                _weatherState.update { it.copy(isLoading = true) }
            }
            .catch { e ->
                _weatherState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
            .collect { result ->
                result.body()?.let { weatherResponse ->
                    _weatherState.update {
                        it.copy(
                            weatherData = WeatherData(
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
                                country = weatherResponse.sys.country,
                                dt = weatherResponse.dt,
                                timezone = weatherResponse.timezone,
                                timestamp = System.currentTimeMillis(),
                            ),
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                }
            }
    }

    fun addWeather(weatherData: WeatherData) = viewModelScope.launch(Dispatchers.IO) {
        weatherUseCase.addWeather(weatherData)
    }

    fun getWeatherHistory() {
        weatherUseCase.getWeathers()
            .onEach { data ->
                _weatherHistoryState.update {
                    it.copy(
                        weatherHistory = data,
                        isLoading = false,
                        errorMessage = null,
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
