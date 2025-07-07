package com.pilabor.resonance.data.model

import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem

data class HomeDataResponse(
    val continueListening: List<MediaSourceItem>,
    val recommendedSongs: List<MediaSourceItem>,
    val topMixes: List<Album>
)