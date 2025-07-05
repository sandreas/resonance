package com.pilabor.resonance
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem
import com.pilabor.resonance.mediaSource.api.model.MediaSourceTrack
import com.pilabor.resonance.service.PlaybackService
// import com.pilabor.resonance.service.PlaybackService.Companion.KEY_SONG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

sealed class PlaySongState {
    object Loading : PlaySongState()
    data class Success(
        val isPlaying: Boolean = false,
        val currentSong: MediaSourceItem? = null,
        val currentPosition: Long = 0L,
        val duration: Long = 0L,
        val error: String? = null,
        val isBuffering: Boolean = false,
    ) : PlaySongState()

    data class Error(val message: String) : PlaySongState()
}

class MainViewModel(): ViewModel() {
    // private var isServiceBound = false
    // private var currentSong: MediaSourceItem? = null


    private val _state = MutableStateFlow<PlaySongState>(PlaySongState.Loading)
    val state: StateFlow<PlaySongState> = _state

    val mediaItem = MediaSourceItem(
        id = "1",
        artists = listOf(),
        tracks = listOf(MediaSourceTrack(
            id = "1",
            url = "https://download.samplelib.com/mp3/sample-15s.mp3",
            duration = 15.seconds
        )),
        genre = "Genre",
        cover = "https://fynder.de/article/liste-mit-freeware-fuer-windows-und-macos-61.html",
        createdAt = 2020,
        releaseDate = 2020,
        title = "Sample 15s",
        updatedAt =2020,
    )

    /*
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            p0: ComponentName?,
            binder: IBinder?
        ) {
            isServiceBound = true
            observePlaybackService()
            currentSong?.let {
                playbackService?.playSong(it)
            } ?: run {
                _state.value = PlaySongState.Error("No song to play")
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isServiceBound = false
            playbackService = null
        }
    }

    init {
        startServiceAndBind(mediaItem)
    }
    */
    init {
    }

/*
    private fun observePlaybackService() {
        val playbackService = PlaybackService.getInstance()
        playbackService?.playerState?.onEach {
            _state.value = PlaySongState.Success(
                isPlaying = it.isPlaying,
                currentPosition = it.currentPosition.coerceAtLeast(0),
                duration = it.duration.coerceAtLeast(0),
                currentSong = it.currentSong,
                isBuffering = it.isBuffering,
                error = it.error
            )
        }?.launchIn(viewModelScope)
    }
*/
    /*
    private fun startServiceAndBind(song: MediaSourceItem) {
        val intent = Intent(context, PlaybackService::class.java).apply {
            action = PlaybackService.ACTION_PLAY
            putExtra(KEY_SONG, song)
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

        if (!isServiceBound) {
            context.bindService(
                intent,
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
    }
*/

    fun onPause() {
        /*
        val playbackService = PlaybackService.getInstance()
        playbackService?.pauseSong()

         */
    }

    fun onJumpForward() {

    }

    fun onJumpBack() {

    }

    fun onNext() {

    }


    fun onPlay() {
        /*
        val playbackService = PlaybackService.getInstance()
        if(playbackService?.playerState?.value?.currentSong == null) {

            playbackService?.playSong(mediaItem)
        } else {
            playbackService?.resumeSong()
        }

         */
        // player.playerState

    }



    fun onSkipStart() {
        // Log.d(this::class.simpleName, "onSkipStart")

    }

}