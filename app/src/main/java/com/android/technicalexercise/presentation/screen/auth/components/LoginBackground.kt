package com.android.technicalexercise.presentation.screen.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginBackground() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .rotate(30F)
                .offset(x = (-85).dp, y = 50.dp)
                .clip(RoundedCornerShape(15))
                .background(MaterialTheme.colorScheme.primaryContainer),
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .rotate(15F)
                .offset(x = (-15).dp, y = 125.dp)
                .clip(RoundedCornerShape(15))
                .background(MaterialTheme.colorScheme.secondary),
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 30.dp, y = 70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )

        // Top Right Corner
        Box(
            modifier = Modifier
                .size(120.dp)
                .rotate(30F)
                .offset(x = 295.dp, y = (-105).dp)
                .clip(RoundedCornerShape(15))
                .background(MaterialTheme.colorScheme.secondary),
        )

        // Bottom Left Corner
        Box(
            modifier = Modifier
                .size(170.dp)
                .offset(x = (-85).dp, y = 750.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
        )

        // Bottom Right Corner
        Box(
            modifier = Modifier
                .size(200.dp)
                .rotate(30F)
                .offset(x = 600.dp, y = 600.dp)
                .clip(RoundedCornerShape(15))
                .background(MaterialTheme.colorScheme.primary),
        )
    }
}

@Preview
@Composable
fun LoginBackgroundPreview() {
    Surface {
        LoginBackground()
    }
}
