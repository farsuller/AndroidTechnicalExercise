package com.android.technicalexercise.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.android.technicalexercise.presentation.screen.home.components.HomeTopBar
import com.android.technicalexercise.presentation.screen.home.components.WeatherContent

@Composable
fun HomeScreen(weatherState: WeatherState, onSignOutClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar(onSignOutClick = onSignOutClick) },
    ) { innerPadding ->

        WeatherContent(
            paddingValues = innerPadding,
            weatherState = weatherState,
        )
    }
}
