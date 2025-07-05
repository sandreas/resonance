package com.pilabor.resonance.notification

// import org.koin.core.annotation.Single
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pilabor.resonance.MainActivity
import com.pilabor.resonance.MainApp
import com.pilabor.resonance.mediaSource.api.interfaces.PlaybackNotificationMeta
import com.pilabor.resonance.service.PlaybackService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

// @Single
class NotificationHelper() {

    companion object {
        val context = MainApp.getContext()
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())


        const val CHANNEL_ID = "resonance_notification_channel"
        const val CHANNEL_NAME = "Resonance Notification Channel"
        const val CHANNEL_DESCRIPTION = "Channel for Resonance playback notifications"
        const val NOTIFICATION_ID = 1

        @SuppressLint("ObsoleteSdkInt")
        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = CHANNEL_DESCRIPTION
                    setSound(null, null)
                    setShowBadge(false)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                }
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }


        fun createPlayerNotification(mediaSession: MediaSessionCompat, meta: PlaybackNotificationMeta?, callBack: (Notification) -> Unit): Notification? {
            if(meta == null) {
                return null
            }

            val intent = Intent(
                context,
                MainActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            var toggleAction = if(meta.isPlaying)
                createAction(PlaybackService.ACTION_PAUSE, "Pause", android.R.drawable.ic_media_pause)
            else
                createAction(PlaybackService.ACTION_PLAY, "Play", android.R.drawable.ic_media_play)

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(meta.contentTitle)
                .setContentText(meta.contentText)
                // com.pilabor.resonance.R.drawable.ic_launcher_background
                .setSmallIcon(meta.smallIcon)
                .setContentIntent(pendingIntent)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.sessionToken)
                        .setShowActionsInCompactView(0)
                )
                .setOngoing(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
                .addAction(createAction(PlaybackService.ACTION_PREVIOUS, "Previous", android.R.drawable.ic_media_previous))
                .addAction(createAction(PlaybackService.ACTION_NEXT, "Next", android.R.drawable.ic_media_next))
                .addAction(toggleAction)
            // todo
            // .addAction(createAction(PlaybackService.ACTION_FORWARD, "Forward", android.R.drawable.ic_media_next))
            // .addAction(createAction(PlaybackService.ACTION_BACK, "Back", android.R.drawable.ic_media_previous))

            val notification = notificationBuilder.build()
            notification.flags = Notification.FLAG_NO_CLEAR or Notification.FLAG_ONGOING_EVENT
            callBack(notification)
            loadAlbumIcon(notificationBuilder, meta.largeIconUrl, callBack)
            return notification
        }

        fun loadAlbumIcon(
            builder: NotificationCompat.Builder, url: String, callBack: (Notification) -> Unit
        ) {
            try {
                scope.launch {

                    val bitmap = withContext(Dispatchers.IO) {
                        try {
                            val imageUrl = URL(url)
                            val connection = imageUrl.openConnection()
                            connection.connect()
                            val input = connection.getInputStream()
                            val bitmap = BitmapFactory.decodeStream(input)
                            bitmap
                        } catch (_: Exception) {
                            null
                        }
                    }
                    bitmap?.let {
                        builder.setLargeIcon(it)
                        callBack(builder.build())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * Creates an intent action based on simplifed parameters
         */
        private fun createAction(intentAction:String, label: String, icon: Int): NotificationCompat.Action {
            val intent = Intent(context, PlaybackService::class.java).apply {
                action = intentAction
            }

            val pendingIntent = PendingIntent.getService(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            return NotificationCompat.Action(
                icon,
                label,
                pendingIntent
            )
        }



        fun updateNotification(notification: Notification) {
            notification.flags =
                notification.flags or Notification.FLAG_NO_CLEAR or Notification.FLAG_ONGOING_EVENT
            try {
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
            } catch (e: SecurityException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




}