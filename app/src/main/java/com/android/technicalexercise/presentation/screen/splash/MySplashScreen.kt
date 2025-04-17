package com.android.technicalexercise.presentation.screen.splash

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.android.technicalexercise.MainActivity
import com.android.technicalexercise.R
import com.android.technicalexercise.domain.model.Location
import com.android.technicalexercise.presentation.navigation.HomeRoute
import com.android.technicalexercise.presentation.navigation.LoginRoute
import com.android.technicalexercise.presentation.screen.auth.AuthState
import com.android.technicalexercise.presentation.screen.home.HomeViewModel
import com.android.technicalexercise.util.LocationUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun MySplashScreen(navController: NavController, authState: State<AuthState>) {
    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context) }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(HomeRoute)
            is AuthState.Error, AuthState.Unauthenticated -> navController.navigate(LoginRoute)
            else -> navController.navigate(LoginRoute)
        }
    }

    DisplayLocationPopUp(locationUtils = locationUtils)
}

@Composable
fun DisplayLocationPopUp(locationUtils: LocationUtils) {
    val context = LocalContext.current
    val viewModel = koinViewModel<HomeViewModel>()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->

            val hasPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (hasPermission) {
                locationUtils.requestLocationUpdates { lat, lon ->
                    viewModel.updateLocation(Location(lat, lon))
                }
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )

                if (rationaleRequired) {
                    Toast.makeText(context, "Location Permission Required", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Enable Location Permission From Settings",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        },
    )

    LaunchedEffect(Unit) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdates { latitude, longitude ->
                viewModel.updateLocation(Location(latitude, longitude))
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            locationUtils.stopLocationUpdates()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.cloud_circle),
            contentDescription = "Logo Image",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
