package com.android.technicalexercise.util

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil3.ImageLoader
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun provideImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
}

fun kelvinToCelsius(kelvin: Double): Double {
    return kelvin - 273.15
}

fun convertUnixToReadableTime(unixTimestamp: Int, timezoneOffset: Int): String {
    val instant = Instant.ofEpochSecond(unixTimestamp.toLong())
    val zoneId = ZoneId.ofOffset("UTC", java.time.ZoneOffset.ofTotalSeconds(timezoneOffset))
    val formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(zoneId)
    return formatter.format(instant)
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    return email.matches(emailRegex.toRegex())
}