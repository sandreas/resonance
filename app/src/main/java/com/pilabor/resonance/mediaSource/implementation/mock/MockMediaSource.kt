package com.codewithfk.musify_android.mediaSource.implementation.mock

import com.codewithfk.musify_android.data.model.Artist
import com.codewithfk.musify_android.mediaSource.api.MediaSourceAction
import com.codewithfk.musify_android.mediaSource.api.MediaSourceInterface
import com.codewithfk.musify_android.mediaSource.api.MediaSourceQueryInterface
import com.codewithfk.musify_android.mediaSource.api.model.MediaSourceItem
import com.codewithfk.musify_android.mediaSource.api.model.MediaSourcePerson
import com.codewithfk.musify_android.mediaSource.api.model.MediaSourceTrack
import okhttp3.internal.immutableListOf
import kotlin.time.Duration.Companion.milliseconds

class MockMediaSource(override val id: String, override val name: String) : MediaSourceInterface {
    val artists = immutableListOf(
        MediaSourcePerson(id="1", name="Peter Packet", bio="Peter Packet's bio", profilePicture="", createdAt=0L, updatedAt=0L)
    )
    val tracks = immutableListOf(
        MediaSourceTrack("B00UWZKNYQ", "https://samples.audible.de/bk/adko/002062/bk_adko_002062_sample.mp3", 600000.milliseconds)
    )


    val songs = mutableListOf(
        MediaSourceItem(
            id = "B00UWZKNYQ",
            title = "Zeiten des Sturms",
            artists = immutableListOf(artists[0]),
            tracks = immutableListOf(tracks[0]),
            cover = "https://m.media-amazon.com/images/I/61Am6kq5sqL._SL500_.jpg",
            createdAt = 0L,
            genre = "Fantasy",
            releaseDate = 0L,
            updatedAt = 0L
        )
    )

    override suspend fun query(query: MediaSourceQueryInterface): List<MediaSourceItem> {
        return songs
    }

    override suspend fun getItemById(id: String): MediaSourceItem? {
        return songs.find { it -> it.id == id};
    }

    override suspend fun performAction(
        item: MediaSourceItem,
        action: MediaSourceAction,
        context: Any?
    ): MediaSourceItem {
        return item
    }
}