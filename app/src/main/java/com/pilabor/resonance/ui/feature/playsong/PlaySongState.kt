package com.pilabor.resonance.ui.feature.playsong

import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem

sealed class PlaySongState {
    object Loading : PlaySongState()
    data class Success(
        val isPlaying: Boolean = false,
        val currentSong: MediaSourceItem? = null,
        val currentPosition: Long = 0L,
        val duration: Long = 0L,
        val error: String? = null,
        val isBuffering: Boolean = false,
    ) : PlaySongState()

    data class Error(val message: String) : PlaySongState()
}