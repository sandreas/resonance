package com.codewithfk.musify_android

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.view.KeyEvent
import androidx.media.session.MediaButtonReceiver

class CustomMediaButtonReceiver() : MediaButtonReceiver() {


    override fun onReceive(context: Context?, intent: Intent) {
        if (Intent.ACTION_MEDIA_BUTTON != intent.action) {
            return
        }
        val keyEvent = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
        }
        if(keyEvent == null) {
            return
        }

        keyEvent.let {
            /*
            when (it.action) {
                KeyEvent.ACTION_DOWN -> {
                    // Handle the key press here immediately
                    // For example, trigger the media action
                    handleMediaButton(it.keyCode)
                }
            }

             */

            Log.d(
                "MediaButtonReceiver",
                "=== debounceKeyEvent: ${keyEventToString(keyEvent)}"
            )
            // Prevent further processing by the system
            if(isOrderedBroadcast) {
                abortBroadcast()
            }
        }
    }

    private fun keyEventToString(keyEvent: KeyEvent): String {
        var action = ""
        when (keyEvent.action) {
            KeyEvent.ACTION_UP -> {
                action = "ACTION_UP"
            }
            KeyEvent.ACTION_DOWN  -> {
                action = "ACTION_DOWN"
            }
            else -> action="ACTION_UNKNOWN"
        }

        var keyCode = ""
        when (keyEvent.keyCode) {
            KeyEvent.KEYCODE_HEADSETHOOK -> {
                keyCode = "KEYCODE_HEADSETHOOK"
            }
            KeyEvent.KEYCODE_MEDIA_PLAY -> {
                keyCode = "KEYCODE_MEDIA_PLAY"
            }
            KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                keyCode = "KEYCODE_MEDIA_PAUSE"
            }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE  -> {
                keyCode = "KEYCODE_MEDIA_PLAY_PAUSE"
            }

            KeyEvent.KEYCODE_MEDIA_NEXT -> {
                keyCode = "KEYCODE_MEDIA_NEXT"
            }

            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                keyCode = "KEYCODE_MEDIA_PREVIOUS"
            }

            KeyEvent.KEYCODE_MEDIA_STOP -> {
                keyCode = "KEYCODE_MEDIA_STOP"
            }
        }

        return "keyCode=$keyCode, action=$action, repeatCount=${keyEvent.repeatCount}"
    }

}