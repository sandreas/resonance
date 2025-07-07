package com.codewithfk.musify_android.ui.feature.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codewithfk.musify_android.ui.feature.widgets.ErrorScreen
import com.codewithfk.musify_android.ui.feature.widgets.LoadingScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import com.codewithfk.musify_android.R
import com.codewithfk.musify_android.ui.feature.widgets.MusifySpacer
import com.codewithfk.musify_android.ui.feature.widgets.MusifyTextField
import com.codewithfk.musify_android.ui.feature.widgets.SocialCard
import com.codewithfk.musify_android.ui.navigation.HomeRoute
import com.codewithfk.musify_android.ui.navigation.RegisterRoute
import com.codewithfk.musify_android.ui.theme.MusifyAndroidTheme

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {

    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()
    val isRememberMe = viewModel.isRememberMeActive.collectAsStateWithLifecycle()
    val isPasswordVisible = viewModel.isPasswordVisible.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.event.collectLatest {
            when (it) {
                is LoginEvent.showErrorMessage -> {
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is LoginEvent.NavigateToRegister -> {
                    navController.navigate(RegisterRoute)
                }

                is LoginEvent.NavigateToHome -> {
                    navController.navigate(HomeRoute)
                }
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()
    when (state.value) {
        is LoginState.Loading -> {
            LoadingScreen()
        }

        is LoginState.Success -> {
        }

        is LoginState.Error -> {
            val errorMessage = (state.value as LoginState.Error).message
            ErrorScreen(errorMessage, "Retry", onPrimaryButtonClicked = {
                viewModel.onRetryClicked()
            })
        }

        LoginState.Nothing -> {

            LoginScreenContent(

                email = email.value,
                pass = password.value,
                rememberMe = isRememberMe.value,
                isPasswordVisible = isPasswordVisible.value,
                onLoginClicked = {
                    viewModel.onLoginClicked()
                },
                onRegisterClicked = {
                    viewModel.onRegisterClicked()
                },
                onForgotPasswordClicked = {
                    viewModel.onForgotPasswordClicked()
                },
                onShowPassClicked = {
                    viewModel.onPasswordVisibilityChanged()
                },
                onEmailChange = {
                    viewModel.onEmailChanged(it)
                },
                onPasswordChange = {
                    viewModel.onPasswordChanged(it)
                },
                onRememberMeChange = {
                    viewModel.onRememberMeClicked()
                }
            )
        }
    }
}

@Composable
fun LoginScreenContent(
    email: String,
    pass: String,
    rememberMe: Boolean,
    isPasswordVisible: Boolean,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onShowPassClicked: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(painter = painterResource(R.drawable.ic_back), contentDescription = null)
        MusifySpacer(size = 16.dp)
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center),
            )
        }
        MusifySpacer(16.dp)
        Text(
            "Login to your account", style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        MusifySpacer(16.dp)
        MusifyTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.mail),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        MusifySpacer(8.dp)
        MusifyTextField(
            value = pass,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            placeholder = { Text("Enter your email") },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.lock),
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = onShowPassClicked) {
                    Image(
                        painter = painterResource(R.drawable.eye_off),
                        contentDescription = null,
                        colorFilter = if (isPasswordVisible) {
                            androidx.compose.ui.graphics.ColorFilter.tint(
                                MaterialTheme.colorScheme.primary
                            )
                        } else {
                            null
                        }
                    )
                }
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) {
                androidx.compose.ui.text.input.VisualTransformation.None
            } else {
                androidx.compose.ui.text.input.PasswordVisualTransformation()
            },
        )
        MusifySpacer(8.dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = onRememberMeChange,
                modifier = Modifier.padding(start = 16.dp),
                colors = androidx.compose.material3.CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Text("Remember me", fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary)
        }
        MusifySpacer(16.dp)
        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .shadow(
                    8.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = MaterialTheme.colorScheme.primary,
                    spotColor =
                        MaterialTheme.colorScheme.primary
                ),
            onClick = onLoginClicked
        ) {
            Text("Login", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary)
        }

        TextButton(onClick = onForgotPasswordClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Forgot password?", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        }
        Box(modifier = Modifier.weight(1f))
        SocialCard(R.string.do_not_have_an_account, onClick = onRegisterClicked, onFbClick = {}) { }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MusifyAndroidTheme {
        LoginScreenContent(
            "", "", false, false,
            onLoginClicked = {},
            onRegisterClicked = {},
            onForgotPasswordClicked = {},
            onShowPassClicked = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRememberMeChange = {}
        )
    }
}









