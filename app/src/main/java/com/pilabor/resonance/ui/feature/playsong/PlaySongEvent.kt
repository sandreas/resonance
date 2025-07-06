package com.pilabor.resonance.ui.feature.playsong

sealed class PlaySongEvent {
    data class showErrorMessage(val message: String) : PlaySongEvent()
}