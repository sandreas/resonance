package com.pilabor.resonance.ui.navigation

import kotlinx.serialization.Serializable

interface ResonanceNavRoute {}

@Serializable
object OnboardingRoute : ResonanceNavRoute

@Serializable
object LoginRoute : ResonanceNavRoute

@Serializable
object RegisterRoute : ResonanceNavRoute

@Serializable
object HomeRoute : ResonanceNavRoute

@Serializable
data class PlaySongRoute(val id: String) : ResonanceNavRoute
