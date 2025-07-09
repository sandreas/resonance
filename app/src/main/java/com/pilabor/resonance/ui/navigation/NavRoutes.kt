package com.pilabor.resonance.ui.navigation

import kotlinx.serialization.Serializable

interface ResonanceNavRoute {}

@Serializable
object HomeRoute : ResonanceNavRoute

@Serializable
data class PlaySongRoute(val id: String) : ResonanceNavRoute
