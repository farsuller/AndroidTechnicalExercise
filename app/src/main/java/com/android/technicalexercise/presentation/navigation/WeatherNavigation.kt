package com.android.technicalexercise.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.presentation.screen.home.HomeScreen
import com.android.technicalexercise.presentation.screen.home.HomeViewModel
import com.android.technicalexercise.presentation.screen.splash.MySplashScreen
import com.android.technicalexercise.util.LocationUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            MySplashScreen(navController = navController)
        }

        composable<LoginRoute> {

        }
        composable<SignUpRoute> {

        }
        composable<HomeRoute> {
            val homeViewModel: HomeViewModel = koinViewModel()

            val location  = homeViewModel.location.value
            val weatherState by homeViewModel.weatherState.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val locationUtils = remember { LocationUtils(context) }

            // Trigger location fetch once
            LaunchedEffect(Unit) {
                if (locationUtils.hasLocationPermission(context)) {
                    locationUtils.requestLocationUpdates { latitude, longitude ->
                        homeViewModel.updateLocation(Location(latitude, longitude))
                    }
                } else {
                    Toast.makeText(context, "Location permission not granted", Toast.LENGTH_SHORT).show()
                }
            }

            // Trigger weather fetch ONLY when location becomes non-null
            LaunchedEffect(location) {
                location?.let {
                    homeViewModel.getWeatherData(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                }
            }

            HomeScreen(
                navController = navController,
                weatherState = weatherState,
                location = location,
            )
        }
    }
}