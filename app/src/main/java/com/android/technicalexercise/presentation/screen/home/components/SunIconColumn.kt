package com.android.technicalexercise.presentation.screen.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.technicalexercise.R

@Composable
fun SunIconColumn(
    modifier: Modifier = Modifier,
    label: String,
    timeText: String,
    @DrawableRes icon: Int = R.drawable.sunset,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$label: $timeText")
        Icon(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = icon),
            contentDescription = "$label Icon",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
