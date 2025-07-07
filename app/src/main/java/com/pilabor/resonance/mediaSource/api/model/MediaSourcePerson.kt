package com.codewithfk.musify_android.mediaSource.api.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaSourcePerson(
    val id: String,
    val name: String,
    val bio: String,
    val profilePicture: String,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable
