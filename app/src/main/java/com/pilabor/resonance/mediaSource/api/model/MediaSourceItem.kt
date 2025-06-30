package com.pilabor.resonance.mediaSource.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration.Companion.milliseconds

@Parcelize
data class MediaSourceItem(
    val id: String,
    val artists: List<MediaSourcePerson>,
    val tracks: List<MediaSourceTrack>,
    val genre: String,
    val cover: String,
    val createdAt: Long,
    val releaseDate: Long,
    val title: String,
    val updatedAt: Long,
) : Parcelable {
    val duration get() = (tracks.sumOf {it -> it.duration.inWholeMilliseconds}).milliseconds
    val artist get() = (artists.joinToString(", ") { it -> it.name })
}