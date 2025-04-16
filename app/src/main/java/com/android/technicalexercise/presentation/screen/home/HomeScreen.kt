package com.android.technicalexercise.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.presentation.screen.home.components.HomeTopBar
import com.android.technicalexercise.presentation.screen.home.components.WeatherContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, weatherState: WeatherState, location: Location?) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar(onSignOutClick = {}) }
    ) { innerPadding ->

        WeatherContent(
            paddingValues = innerPadding,
            weatherState = weatherState,
            location = location
        )
    }

}