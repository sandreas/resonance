package com.pilabor.resonance.mediaSource.implementation.filesystem

import com.pilabor.resonance.mediaSource.api.MediaSourceAction
import com.pilabor.resonance.mediaSource.api.MediaSourceInterface
import com.pilabor.resonance.mediaSource.api.MediaSourceQueryInterface
import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem
import okhttp3.internal.immutableListOf

class FileSystemMediaSource(override val id: String, override val name: String) : MediaSourceInterface {
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