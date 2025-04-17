package com.android.technicalexercise.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.android.technicalexercise.R
import com.android.technicalexercise.util.provideImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onSignOutClick: () -> Unit) {
    val context = LocalContext.current
    val imageLoader = remember { provideImageLoader(context) }

    val painter = rememberAsyncImagePainter(
        model = R.drawable.exit,
        imageLoader = imageLoader,
    )

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
        navigationIcon = {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.cloud_circle),
                contentDescription = "Logo Image",
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        title = {
            Text(
                text = "Weather",
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        actions = {
            IconButton(onClick = onSignOutClick) {
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painter,
                    contentDescription = null,
                )
            }
        },
    )
}
