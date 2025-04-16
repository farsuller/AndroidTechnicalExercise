package com.android.technicalexercise.presentation.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.technicalexercise.R
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.presentation.screen.home.WeatherState
import com.android.technicalexercise.util.Tabs
import com.android.technicalexercise.util.convertUnixToReadableTime
import com.android.technicalexercise.util.isNightTime


@Composable
fun WeatherContent(
    paddingValues: PaddingValues,
    weatherState: WeatherState,
    location: Location?
) {
    var selectedTabIndex by remember { mutableIntStateOf(Tabs.HOME.ordinal) }
    val tabs = Tabs.entries

    val weatherIconNow = remember(weatherState) {

        val isNight = isNightTime(
            unixSeconds = System.currentTimeMillis() / 1000,
            timezoneOffsetInSeconds = weatherState.timezone
        )

        when {
            isNight -> R.drawable.night2
            weatherState.weatherMain == "Clear" -> R.drawable.sun
            weatherState.weatherMain == "Rain" || weatherState.weatherMain == "Moderate Rain" -> R.drawable.rain
            weatherState.weatherMain == "Clouds" -> R.drawable.cloudy
            weatherState.weatherMain == "Partially cloudy" -> R.drawable.sun
            weatherState.weatherMain == "Thunder" -> R.drawable.thunder
            else -> R.drawable.sun
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text(tab.title) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title
                        )
                    }
                )
            }
        }

        when (tabs[selectedTabIndex]) {
            Tabs.HOME -> {
                when {
                    weatherState.isLoading -> ContentLoading()
                    weatherState.errorMessage != null -> ContentError(weatherState.errorMessage)
                    else -> {
                        val sunriseText =
                            convertUnixToReadableTime(weatherState.sunrise, weatherState.timezone)
                        val sunsetText =
                            convertUnixToReadableTime(weatherState.sunset, weatherState.timezone)

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Text(text = "Area: ${weatherState.cityName}, ${weatherState.country}")

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(modifier = Modifier.fillMaxWidth()) {
                                SunIconColumn(
                                    modifier = Modifier.weight(1f),
                                    label = "Sunrise",
                                    timeText = sunriseText,
                                    icon = R.drawable.sunrise
                                )
                                SunIconColumn(
                                    modifier = Modifier.weight(1f),
                                    label = "Sunset",
                                    timeText = sunsetText,
                                    icon = R.drawable.sunset
                                )
                            }

                            WeatherCard(weatherState = weatherState, iconImage = weatherIconNow)

                            Spacer(modifier = Modifier.height(20.dp))

                        }
                    }
                }
            }

            Tabs.HISTORY -> {

            }
        }
    }
}






