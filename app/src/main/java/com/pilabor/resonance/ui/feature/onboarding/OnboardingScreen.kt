package com.codewithfk.musify_android.ui.feature.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codewithfk.musify_android.R
import com.codewithfk.musify_android.ui.feature.widgets.HighlightedText
import com.codewithfk.musify_android.ui.navigation.LoginRoute
import com.codewithfk.musify_android.ui.navigation.OnboardingRoute
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    navController: NavController, viewModel: OnboardingViewModel = koinViewModel()
) {
    LaunchedEffect(true) {
        viewModel.event.collectLatest {
            when (it) {
                is OnboardingEvent.showErrorMessage -> {
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is OnboardingEvent.NavigateToLogin -> {
                    navController.navigate(LoginRoute){
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()
    val cardHeight = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    when (state.value) {
        is OnboardingState.Normal -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(R.drawable.welcome),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(androidx.compose.ui.Alignment.BottomCenter)
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_girl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-(cardHeight.value.minus(20.dp)))),
                        contentScale = ContentScale.Crop
                    )
                    OnboardingCard(
                        modifier = Modifier
                            .align(androidx.compose.ui.Alignment.BottomCenter)
                            .onGloballyPositioned {
                                val heightPX = it.size.height
                                cardHeight.value = with(density) { heightPX.toDp() }
                            },
                        onClick = {
                            viewModel.onGetStartedClicked()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingCard(modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(32.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {


        HighlightedText(
            textResourceID = R.string.onboarding_text,
            spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(16.dp),
            textStyle = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(70.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(5.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
        Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
            Text("Get Started", style = MaterialTheme.typography.labelLarge)
        }
    }
}