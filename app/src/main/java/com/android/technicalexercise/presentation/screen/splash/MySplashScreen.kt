package com.android.technicalexercise.presentation.screen.splash

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.android.technicalexercise.presentation.screen.home.HomeViewModel
import com.android.technicalexercise.util.LocationUtils
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@Composable
fun MySplashScreen(navController: NavController) {
    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context) }

    DisplayLocationPopUp(locationUtils = locationUtils, navController = navController)
}

@Composable
fun DisplayLocationPopUp(
    locationUtils: LocationUtils,
    navController: NavController,
) {
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
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(context, "Location Permission Required", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Enable Location Permission From Settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    )

    LaunchedEffect(viewModel) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdates { latitude, longitude ->
                viewModel.updateLocation(Location(latitude, longitude))
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            locationUtils.stopLocationUpdates()
        }
    }

    val location = viewModel.location.value

    LaunchedEffect(location) {
        if (location != null) {
            delay(1000L)
            navController.popBackStack()
            navController.navigate(HomeRoute)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.cloud_circle),
            contentDescription = "Logo Image",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}