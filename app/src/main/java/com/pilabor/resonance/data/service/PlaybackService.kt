package com.pilabor.resonance.data.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.session.MediaButtonReceiver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.pilabor.resonance.data.helper.NotificationHelper
import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PlaybackService : Service() {

    companion object {
        const val ACTION_PLAY = "com.pilabor.resonance.ACTION_PLAY"
        const val ACTION_PAUSE = "com.pilabor.resonance.ACTION_PAUSE"
        const val ACTION_STOP = "com.pilabor.resonance.ACTION_STOP"
        const val ACTION_PREVIOUS = "com.pilabor.resonance.ACTION_PREVIOUS"
        const val ACTION_NEXT = "com.pilabor.resonance.ACTION_NEXT"
        const val ACTION_PREPARE_SONG = "com.pilabor.resonance.ACTION_PREPARE_SONG"

        val KEY_SONG = "SONG"
    }

    val mediaSessionCallBack = MediaSessionCallback(this)

    private val binder = MusicBinder()
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _player = MutableStateFlow<PlayerState>(PlayerState())
    val playerState = _player.asStateFlow()

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private val notificationHelper: NotificationHelper by inject()


    private var positionUpdateJob: Job? = null
    private var notificationUpdateJob: Job? = null

    inner class MusicBinder : Binder() {
        fun getService(): PlaybackService = this@PlaybackService
    }

    val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    _player.value = playerState.value.copy(
                        isBuffering = true,
                        currentPosition = exoPlayer.currentPosition,
                        duration = exoPlayer.duration,
                        error = null,
                        isPlaying = false
                    )
                    updatePlaybackState(PlaybackStateCompat.STATE_BUFFERING)
                    updateMediaSessionState()
                }

                Player.STATE_READY -> {
                    _player.value = playerState.value.copy(
                        isPlaying = exoPlayer.isPlaying,
                        currentPosition = exoPlayer.currentPosition,
                        duration = exoPlayer.duration,
                        error = null,
                        isBuffering = false
                    )

                    if (exoPlayer.isPlaying) {
                        startForegroundServiceIfNeeded()
                        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
                    } else {
                        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
                    }
                    updateMediaSessionState()
                }

                Player.STATE_ENDED -> {
                    _player.value = playerState.value.copy(
                        isPlaying = false,
                        currentPosition = 0L,
                        duration = 0L,
                        error = null,
                        isBuffering = false
                    )
                    updatePlaybackState(PlaybackStateCompat.STATE_STOPPED)
                    updateMediaSessionState()
                }

                Player.STATE_IDLE -> {
                    _player.value = playerState.value.copy(
                        isPlaying = false,
                        currentPosition = 0L,
                        duration = 0L,
                        error = null,
                        isBuffering = false
                    )
                    updatePlaybackState(PlaybackStateCompat.STATE_NONE)
                    updateMediaSessionState()
                }
            }
        }
    }

    private fun updatePlaybackState(state: Int) {
        val position = exoPlayer.currentPosition
        val state = PlaybackStateCompat.Builder().setState(state, position, 1f)
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or PlaybackStateCompat.ACTION_STOP or PlaybackStateCompat.ACTION_SEEK_TO or PlaybackStateCompat.ACTION_SKIP_TO_NEXT or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            ).build()
        mediaSession.setPlaybackState(state)
    }

    private fun updateMediaSessionState() {
        if (exoPlayer.isPlaying || _player.value.currentSong != null) {
            if (!mediaSession.isActive) {
                mediaSession.isActive = true
            }
        } else {
            if (mediaSession.isActive) {
                mediaSession.isActive = false
            }
        }
    }



    override fun onCreate() {
        super.onCreate()

        exoPlayer = ExoPlayer.Builder(this).build().also {
            it.playWhenReady = true
            it.addListener(playerListener)
        }

        mediaSession = MediaSessionCompat(this, "ResonancePlaybackService").also {
            it.isActive = true
            it.setCallback(mediaSessionCallBack)

            it.setPlaybackState(
                PlaybackStateCompat.Builder().setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
                    .setActions(
                        PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or PlaybackStateCompat.ACTION_STOP or PlaybackStateCompat.ACTION_SEEK_TO or PlaybackStateCompat.ACTION_SKIP_TO_NEXT or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    ).build()
            )
            // it.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS or MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS)
            // it.setMediaButtonReceiver()
        }
        startPositionUpdate()
    }

    private fun startPositionUpdate() {
        positionUpdateJob?.cancel()
        positionUpdateJob = serviceScope.launch {
            while (true) {
                if (exoPlayer.isPlaying) {
                    _player.value = playerState.value.copy(
                        currentPosition = exoPlayer.currentPosition,
                        duration = exoPlayer.duration,
                        isBuffering = exoPlayer.isLoading,
                        isPlaying = exoPlayer.isPlaying,
                        error = null
                    )
                }
                kotlinx.coroutines.delay(500)
            }
        }
    }

    var isForegroundService = false
    var currentNotification: Notification? = null

    fun startForegroundServiceIfNeeded() {
        val currentSong = playerState.value.currentSong ?: return
        if (!isForegroundService) {
            notificationHelper.createPlayerNotification(
                playerState.value.isPlaying, currentSong, mediaSession
            ) {
                if (!isForegroundService) {
                    try {
                        currentNotification = it
                        startForeground(
                            NotificationHelper.NOTIFICATION_ID, it
                        )
                        isForegroundService = true
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                } else {
                    currentNotification = it
                    updateNotification()
                }
            }
        } else {
            updateNotification()
        }

    }

    fun updateNotification() {
        notificationUpdateJob?.cancel()
        notificationUpdateJob = serviceScope.launch {
            notificationHelper.createPlayerNotification(
                playerState.value.isPlaying,
                playerState.value.currentSong ?: return@launch,
                mediaSession
            ) {
                try {
                    currentNotification = it
                    notificationHelper.updateNotification(it)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    fun stopForegroundService() {
        if (isForegroundService) {
            try {
                mediaSession.isActive = false
                stopForeground(Service.STOP_FOREGROUND_REMOVE)
                isForegroundService = false
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null) {
            MediaButtonReceiver.handleIntent(mediaSession, intent)
        }
        // Handle the intent actions here
        when (intent?.action) {
            ACTION_PLAY -> {
                // sandreas Todo: what is this???
                // how can an intent contain a custom Class?
                val song = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(KEY_SONG, MediaSourceItem::class.java)
                } else {
                    intent.getParcelableExtra<MediaSourceItem>(KEY_SONG)
                }


                if (song != null) {
                    playSong(song)
                } else {
                    if (_player.value.currentSong != null) {
                        resumeSong()
                    }
                }
            }

            ACTION_PAUSE -> {
                pauseSong()
            }

            ACTION_STOP -> {

            }

            ACTION_PREVIOUS -> {

            }

            ACTION_NEXT -> {

            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    fun playSong(song: MediaSourceItem) {
        try {
            _player.value = playerState.value.copy(
                currentSong = song,
                isBuffering = true,
                currentPosition = 0L,
                duration = song.duration.inWholeMilliseconds
            )
            val metaBuilder = MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, song.cover)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, song.cover)
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.id)

            mediaSession.setMetadata(metaBuilder.build())

            // val mediaItem = MediaItem.fromUri(song.audioUrl.toUri())
            // exoPlayer.setMediaItem(mediaItem)

            val mediaItems = song.tracks.map {it -> MediaItem.fromUri(it.url)};
            exoPlayer.setMediaItems(mediaItems)

            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        } catch (ex: Exception) {
            _player.value = playerState.value.copy(
                error = ex.message,
                isBuffering = false,
                currentSong = null
            )
            ex.printStackTrace()
        }
    }

    fun pauseSong() {
        try {
            exoPlayer.pause()
            _player.value = playerState.value.copy(
                isPlaying = false,
                currentPosition = exoPlayer.currentPosition,
                duration = exoPlayer.duration
            )
        } catch (ex: Exception) {
            _player.value = playerState.value.copy(
                error = ex.message,
                isBuffering = false,
                currentSong = null
            )
            ex.printStackTrace()
        }
        updateNotification()
    }

    fun seekTo(pos: Long) {
        exoPlayer.seekTo(pos)
        _player.value = playerState.value.copy(
            currentPosition = pos,
            duration = exoPlayer.duration,
            isBuffering = exoPlayer.isLoading,
            isPlaying = exoPlayer.isPlaying,
            error = null
        )
    }

    /* CUSTOM sandreas! */
    fun seek(offset: Long): Long {
        val currentPos = exoPlayer.currentPosition
        var newPosition = currentPos + offset
        if (newPosition < 0) {
            newPosition = 0;
        } else if (newPosition > exoPlayer.duration) {
            newPosition = exoPlayer.duration - 1;
        }
        exoPlayer.seekTo(newPosition)
        _player.value = playerState.value.copy(
            currentPosition = newPosition,
            duration = exoPlayer.duration
        )
        return newPosition
    }

    fun isPlaying(): Boolean {
        return exoPlayer.isPlaying
    }

    fun resumeSong() {
        try {
            exoPlayer.play()
            _player.value = playerState.value.copy(
                isPlaying = true,
                currentPosition = exoPlayer.currentPosition,
                duration = exoPlayer.duration
            )
            startForegroundServiceIfNeeded()
        } catch (ex: Exception) {
            _player.value = playerState.value.copy(
                error = ex.message,
                isBuffering = false,
                currentSong = null
            )
            ex.printStackTrace()
        }
        updateNotification()
    }
}

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentSong: MediaSourceItem? = null,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val error: String? = null,
    val isBuffering: Boolean = false,
)