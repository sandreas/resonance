package com.codewithfk.musify_android.data.repository

import com.codewithfk.musify_android.data.model.Artist
import com.codewithfk.musify_android.data.model.Song
import com.codewithfk.musify_android.data.network.ApiService
import com.codewithfk.musify_android.data.network.Resource
import org.koin.core.annotation.Single

@Single
class MusicRepository(/*private val apiService: ApiService*/) {
    val artists = mutableListOf(
        Artist(id="1", name="An artist name", bio="A bio", createdAt=0L, profilePicture="", updatedAt=0L)
    )
    val songs = mutableListOf(
        Song(artist=artists[0],
            audioUrl="https://samples.audible.de/bk/adko/002062/bk_adko_002062_sample.mp3",
            coverImage="https://m.media-amazon.com/images/I/61Am6kq5sqL._SL500_.jpg",
            createdAt=0L,
            duration=10*60*1000,
            genre="Fantasy",
            id="B00UWZKNYQ",
            releaseDate=0L,
            title="Zeiten des Sturms",
            updatedAt=0L )
    )
    suspend fun getSongById(id: String): Resource<Song> {
        return try {
            val song = songs.find { s -> s.id == id }

            if(song == null) {
                Resource.Error("Failed to fetch song data")
            } else {
                Resource.Success(song)
            }
            /*
            val response = apiService.getSongById(id)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to fetch song data")
            }


             */
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

}