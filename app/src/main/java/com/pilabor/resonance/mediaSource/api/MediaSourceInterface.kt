package com.pilabor.resonance.mediaSource.api

import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem

interface MediaSourceInterface {
    val id : String
    val name : String

    suspend fun query(query: MediaSourceQueryInterface) : List<MediaSourceItem>
    suspend fun getItemById(id: String) : MediaSourceItem?
    suspend fun performAction(item: MediaSourceItem, action: MediaSourceAction, context:Any?) : MediaSourceItem
}