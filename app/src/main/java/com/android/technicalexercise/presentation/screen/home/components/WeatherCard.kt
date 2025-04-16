package com.android.technicalexercise.presentation.screen.home.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.android.technicalexercise.presentation.screen.home.WeatherState
import com.android.technicalexercise.ui.theme.AndroidTechnicalExerciseTheme
import com.android.technicalexercise.ui.theme.Elevation
import com.android.technicalexercise.ui.theme.Grey
import com.android.technicalexercise.util.formatUnixToLocalTime
import com.android.technicalexercise.util.kelvinToCelsius
import com.android.technicalexercise.util.provideImageLoader
import java.time.Instant
import java.time.ZoneOffset


@SuppressLint("DefaultLocale")
@Composable
fun WeatherCard(
    weatherState: WeatherState,
    @DrawableRes iconImage: Int,
) {
    val context = LocalContext.current
    val imageLoader = remember { provideImageLoader(context) }

    val painter = rememberAsyncImagePainter(
        model = iconImage,
        imageLoader = imageLoader,
    )

    val localTimeString = formatUnixToLocalTime(weatherState.dt.toLong(), weatherState.timeZone)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevation.level4
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(containerColor = Grey)
    ) {

        Box(
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier.offset(x = 70.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                CircleBackground()
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp),
                        painter = painter,
                        contentDescription = null
                    )

                    Text(
                        text = "Now",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = localTimeString,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }


                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    val formattedTemp =
                        String.format("%.2f", kelvinToCelsius(weatherState.temperature))
                    Text(
                        text = "Temperature: $formattedTemp°C",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = "Weather: ${weatherState.weatherMain}",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Description: ${
                            weatherState.weatherDescription
                                ?.split(" ")
                                ?.joinToString(" ")
                                { word ->
                                    word.replaceFirstChar { it.uppercase() }
                                } ?: ""
                        }",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface)

                    val formattedTempC =
                        String.format("%.2f", kelvinToCelsius(weatherState.feelsLike))
                    Text(
                        text = "Feels Like: $formattedTempC°C",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                    Text(
                        text = "Humidity: ${weatherState.humidity}%",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                    Text(
                        text = "Wind Speed: ${weatherState.windSpeed} m/s",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                }
            }
        }

    }
}


@Preview
@Composable
private fun WeatherCardPreview() {
    AndroidTechnicalExerciseTheme {
        Surface {
            WeatherCard(weatherState = WeatherState(), iconImage = 0)
        }
    }
}