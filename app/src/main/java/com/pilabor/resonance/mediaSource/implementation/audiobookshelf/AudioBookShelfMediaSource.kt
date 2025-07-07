package com.codewithfk.musify_android.mediaSource.implementation.audiobookshelf

import com.codewithfk.musify_android.mediaSource.api.MediaSourceAction
import com.codewithfk.musify_android.mediaSource.api.MediaSourceInterface
import com.codewithfk.musify_android.mediaSource.api.MediaSourceQueryInterface
import com.codewithfk.musify_android.mediaSource.api.model.MediaSourceItem
import okhttp3.internal.immutableListOf

class AudioBookShelfMediaSource(override val id: String, override val name: String) : MediaSourceInterface {
    override suspend fun query(query: MediaSourceQueryInterface): List<MediaSourceItem> {
        return immutableListOf()
    }

    override suspend fun getItemById(id: String): MediaSourceItem? {
        return null
    }

    override suspend fun performAction(
        item: MediaSourceItem,
        action: MediaSourceAction,
        context: Any?
    ): MediaSourceItem {
        return item
    }
}