package com.codewithfk.musify_android

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController

import com.codewithfk.musify_android.ui.navigation.AppNavGraph
import com.codewithfk.musify_android.ui.navigation.HomeRoute
import com.codewithfk.musify_android.ui.navigation.OnboardingRoute
import com.codewithfk.musify_android.ui.theme.MusifyAndroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private var isSplashScreenVisible = true
    val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { isSplashScreenVisible }
            setOnExitAnimationListener { splashScreenViewProvider ->
                val zoomX = ObjectAnimator.ofFloat(
                    splashScreenViewProvider.iconView, "scaleX", 0.4f, 0f
                )
                val zoomY = ObjectAnimator.ofFloat(
                    splashScreenViewProvider.iconView, "scaleY", 0.4f, 0f
                )
                zoomX.duration = 300
                zoomY.duration = 300
                zoomY.doOnEnd {
                    splashScreenViewProvider.remove()
                }
                zoomX.doOnEnd {
                    splashScreenViewProvider.remove()
                }
                zoomX.start()
                zoomY.start()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusifyAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavGraph(
                            navController = rememberNavController(),
                            startDestination = HomeRoute // if(mainViewModel.isUserLoggedIn()) HomeRoute else OnboardingRoute
                        )
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            isSplashScreenVisible = false
        }
    }
}