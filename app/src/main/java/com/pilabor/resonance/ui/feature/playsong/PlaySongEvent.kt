package com.codewithfk.musify_android.ui.feature.playsong

sealed class PlaySongEvent {
    data class showErrorMessage(val message: String) : PlaySongEvent()
}