package com.android.technicalexercise.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.presentation.screen.auth.AuthViewModel
import com.android.technicalexercise.presentation.screen.auth.login.LoginScreen
import com.android.technicalexercise.presentation.screen.auth.signup.SignUpScreen
import com.android.technicalexercise.presentation.screen.home.HomeScreen
import com.android.technicalexercise.presentation.screen.home.HomeViewModel
import com.android.technicalexercise.presentation.screen.splash.MySplashScreen
import com.android.technicalexercise.util.LocationUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            val authViewModel: AuthViewModel = koinViewModel()
            val authState = authViewModel.authState.collectAsStateWithLifecycle()

            MySplashScreen(
                navController = navController,
                authState = authState,
            )
        }

        composable<LoginRoute> {
            val authViewModel: AuthViewModel = koinViewModel()
            val authState = authViewModel.authState.collectAsStateWithLifecycle()

            LoginScreen(
                navController = navController,
                authState = authState,
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                navigateToSignUp = {
                    navController.navigate(SignUpRoute)
                },
            )
        }

        composable<SignUpRoute> {
            val authViewModel: AuthViewModel = koinViewModel()

            val authState = authViewModel.authState.collectAsStateWithLifecycle()

            SignUpScreen(
                navController = navController,
                authState = authState,
                onSignUpClick = { email, password ->
                    authViewModel.signup(email, password)
                },
            )
        }

        composable<HomeRoute> {
            val homeViewModel: HomeViewModel = koinViewModel()
            val authViewModel: AuthViewModel = koinViewModel()

            val location = homeViewModel.location.value
            val weatherState by homeViewModel.weatherState.collectAsStateWithLifecycle()
            val weatherHistoryState by homeViewModel.weatherHistoryState.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val locationUtils = remember { LocationUtils(context) }

            LaunchedEffect(Unit) {
                if (locationUtils.hasLocationPermission(context)) {
                    locationUtils.requestLocationUpdates { latitude, longitude ->
                        homeViewModel.updateLocation(Location(latitude, longitude))
                    }
                } else {
                    Toast.makeText(context, "Location permission not granted", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            LaunchedEffect(location) {
                location?.let {
                    homeViewModel.getWeatherData(
                        latitude = it.latitude,
                        longitude = it.longitude,
                    )
                }
            }
            LaunchedEffect(weatherState.weatherData) {
                weatherState.weatherData?.let {
                    homeViewModel.addWeather(it)
                }

                homeViewModel.getWeatherHistory()
            }

            HomeScreen(
                weatherState = weatherState,
                weatherHistoryState = weatherHistoryState,
                onSignOutClick = {
                    authViewModel.signOut()
                    navController.navigate(LoginRoute)
                },
            )
        }
    }
}
