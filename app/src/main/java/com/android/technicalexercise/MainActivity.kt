package com.android.technicalexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.android.technicalexercise.presentation.navigation.WeatherNavigation
import com.android.technicalexercise.ui.theme.AndroidTechnicalExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTechnicalExerciseTheme {
                Surface {
                    val navController = rememberNavController()

                    WeatherNavigation(navController = navController)
                }
            }
        }
    }
}
