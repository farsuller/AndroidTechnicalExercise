package com.android.technicalexercise.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleBackground() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .rotate(30F)
            .offset(x = (125).dp, y = 50.dp)
            .clip(RoundedCornerShape(15))
            .background(MaterialTheme.colorScheme.onTertiary),
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .rotate(15F)
            .offset(x = (65).dp, y = 35.dp)
            .clip(RoundedCornerShape(15))
            .background(MaterialTheme.colorScheme.secondary),
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .offset(x = (75).dp, y = 10.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .rotate(30F)
            .offset(x = 295.dp, y = (-145).dp)
            .clip(RoundedCornerShape(15))
            .background(MaterialTheme.colorScheme.onTertiary),
    )
}

@Preview
@Composable
fun CircleBackgroundPreview() {
    CircleBackground()
}
