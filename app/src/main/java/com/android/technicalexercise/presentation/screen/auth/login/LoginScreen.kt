package com.android.technicalexercise.presentation.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.android.technicalexercise.R
import com.android.technicalexercise.presentation.navigation.HomeRoute
import com.android.technicalexercise.presentation.screen.auth.AuthState
import com.android.technicalexercise.presentation.screen.auth.components.LoginBackground
import com.android.technicalexercise.presentation.screen.auth.components.TextFieldOutline
import com.android.technicalexercise.util.isValidEmail
import com.android.technicalexercise.util.provideImageLoader

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authState: State<AuthState>,
    onLoginClick: (String, String) -> Unit,
    navigateToSignUp: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val imageLoader = remember { provideImageLoader(context) }

    val painter = rememberAsyncImagePainter(
        model = R.drawable.register,
        imageLoader = imageLoader,
    )

    val focusManager = LocalFocusManager.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(HomeRoute)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT,
            ).show()

            else -> Unit
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LoginBackground()

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.cloud_circle),
                contentDescription = "logo",
                tint = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome to Weather App",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextFieldOutline(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it) && it.isNotBlank()
                },
                label = "Email",
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextFieldOutline(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.isNotEmpty() && it.isNotBlank()
                },
                label = "Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.width(150.dp),
                onClick = {
                    onLoginClick(email, password)
                },
                enabled = authState.value != AuthState.Loading && isEmailValid && isPasswordValid,
            ) {
                if (authState.value == AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.surface,
                    )
                } else {
                    Text(text = "Login")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(onClick = navigateToSignUp) {
                    Text(text = "Don't have an account? Sign-up here")
                }

                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navigateToSignUp()
                        },
                    painter = painter,
                    contentDescription = null,
                )
            }
        }
    }
}
