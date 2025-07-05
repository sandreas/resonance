package com.pilabor.resonance.mediaSource.api.interfaces

interface PlaybackNotificationMeta {
    val isPlaying: Boolean
    val contentTitle: String
    val contentText: String
    val smallIcon: Int
    val largeIconUrl: String
}