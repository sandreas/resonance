package com.codewithfk.musify_android.data.model

import com.codewithfk.musify_android.mediaSource.api.model.MediaSourceItem

data class HomeDataResponse(
    val continueListening: List<MediaSourceItem>,
    val recommendedSongs: List<MediaSourceItem>,
    val topMixes: List<Album>
)