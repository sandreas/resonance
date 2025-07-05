package com.pilabor.resonance.mediaSource.api.model

import android.os.Parcelable
import com.pilabor.resonance.R
import com.pilabor.resonance.mediaSource.api.interfaces.PlaybackNotificationMeta
import kotlinx.parcelize.IgnoredOnParcel
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
) : Parcelable, PlaybackNotificationMeta {
    val duration get() = (tracks.sumOf {it -> it.duration.inWholeMilliseconds}).milliseconds
    val artist get() = (artists.joinToString(", ") { it -> it.name })

    @IgnoredOnParcel
    override var isPlaying = false

    override val contentTitle: String
        get() = title
    override val contentText: String
        get() = artist
    override val smallIcon: Int
        get() = R.drawable.ic_launcher_background
    override val largeIconUrl: String
        get() = cover
}