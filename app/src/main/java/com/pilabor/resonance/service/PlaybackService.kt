package com.pilabor.resonance.service
/*
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.pilabor.resonance.MainApp
import com.pilabor.resonance.mediaSource.api.model.MediaSourceItem
import com.pilabor.resonance.notification.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class PlaybackService() : Service() {


    companion object {
        const val KEY_MEDIA_SOURCE_ITEM = "MEDIA_SOURCE_ITEM"
        const val ACTION_SERVICE_START = "com.pilabor.resonance.ACTION_SERVICE_START"
        const val ACTION_SERVICE_STOP = "com.pilabor.resonance.ACTION_SERVICE_STOP"
        const val ACTION_PLAY = "com.pilabor.resonance.ACTION_PLAY"
        const val ACTION_PAUSE = "com.pilabor.resonance.ACTION_PAUSE"
        const val ACTION_TOGGLE = "com.pilabor.resonance.ACTION_TOGGLE"
        const val ACTION_STOP = "com.pilabor.resonance.ACTION_STOP"
        const val ACTION_PREVIOUS = "com.pilabor.resonance.ACTION_PREVIOUS"
        const val ACTION_NEXT = "com.pilabor.resonance.ACTION_NEXT"
        const val ACTION_JUMP_BACK = "com.pilabor.resonance.ACTION_JUMP_BACK"
        const val ACTION_JUMP_FORWARD = "com.pilabor.resonance.ACTION_JUMP_FORWARD"
        const val ACTION_REWIND = "com.pilabor.resonance.ACTION_REWIND"
        const val ACTION_FAST_FORWARD = "com.pilabor.resonance.ACTION_FAST_FORWARD"
        const val ACTION_PREPARE_SONG = "com.pilabor.resonance.ACTION_PREPARE_SONG"


        val context = MainApp.getContext();
        val exoPlayer = ExoPlayer.Builder(context).build();
        public val mediaSessionCallBack = MediaSessionCallback(context.mainLooper, exoPlayer)


        lateinit var mediaSession: MediaSessionCompat

    }

    var isForegroundServiceAlreadyStarted = false
    var currentNotification: Notification? = null
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var notificationUpdateJob: Job? = null
    private var activeMediaSourceItem: MediaSourceItem? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession =
            MediaSessionCompat(this, PlaybackService::class.simpleName ?: "PlaybackService").also {
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
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            // ACTION_SERVICE_START -> start()
            // ACTION_SERVICE_STOP -> stopSelf()
            ACTION_PLAY -> play(intent)
            ACTION_PAUSE -> pause()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun pause() {
        exoPlayer.pause()
        // todo: is this required
        exoPlayer.playWhenReady = false
    }


    private fun play(intent: Intent) {
        try {
            val mediaSourceItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(KEY_MEDIA_SOURCE_ITEM, MediaSourceItem::class.java)
            } else {
                intent.getParcelableExtra<MediaSourceItem>(KEY_MEDIA_SOURCE_ITEM)
            }

            // mediaSourceItem changed
            if(mediaSourceItem != null && mediaSourceItem.id != activeMediaSourceItem?.id) {
                val metaBuilder = MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, mediaSourceItem.title)
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, mediaSourceItem.artist)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, mediaSourceItem.cover)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, mediaSourceItem.cover)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaSourceItem.id)
                mediaSession.setMetadata(metaBuilder.build())
                val mediaItems = mediaSourceItem.tracks.map { it -> MediaItem.fromUri(it.url) };
                exoPlayer.setMediaItems(mediaItems)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                activeMediaSourceItem = mediaSourceItem;
            // no media source item found to play
            } else if (activeMediaSourceItem == null) {
                Log.d("resonance:playbackService", "failed to extract song from intent")
                return
            // just resume playback, because media are identical
            } else {
                exoPlayer.play()
            }

            if(isForegroundServiceAlreadyStarted) {

            } else {
                NotificationHelper.createPlayerNotification(mediaSession, mediaSourceItem) {
                    currentNotification = it
                    if (!isForegroundServiceAlreadyStarted) {
                        try {
                            startForeground(
                                NotificationHelper.NOTIFICATION_ID, it
                            )
                            isForegroundServiceAlreadyStarted = true
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    } else {
                        updateNotification(mediaSourceItem)
                    }
                }

            }
            /*


             */

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    fun updateNotification(mediaSourceItem: MediaSourceItem?) {
        notificationUpdateJob?.cancel()
        notificationUpdateJob = serviceScope.launch {
            NotificationHelper.createPlayerNotification(
                mediaSession,
                mediaSourceItem
            ) {
                try {
                    currentNotification = it
                    NotificationHelper.updateNotification(it)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }



    /*
    fun start() {
        val notification = NotificationHelper.createPlayerNotification(false, null)
        startForeground(NotificationHelper.NOTIFICATION_ID, notification)
    }

     */
}

 */