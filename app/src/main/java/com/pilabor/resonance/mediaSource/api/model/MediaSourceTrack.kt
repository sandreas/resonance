package com.pilabor.resonance.mediaSource.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration

@Parcelize
data class MediaSourceTrack(
    val id: String,
    val url: String,
    val duration: Duration
) : Parcelable