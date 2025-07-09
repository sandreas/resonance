package com.pilabor.resonance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pilabor.resonance.ui.feature.home.HomeScreen
import com.pilabor.resonance.ui.feature.playsong.PlaySongScreen

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: ResonanceNavRoute) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<HomeRoute> {
            HomeScreen(navController)
        }
        composable<PlaySongRoute> {
            val route = it.toRoute<PlaySongRoute>()
            PlaySongScreen(route.id, navController)
        }
    }
}