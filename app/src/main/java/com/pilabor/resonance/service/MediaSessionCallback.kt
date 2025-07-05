package com.pilabor.resonance.service

import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import android.os.Handler
import android.os.Looper


class MediaSessionCallback(
    private val mainLooper: Looper,
    private val exoPlayer: ExoPlayer
): MediaSessionCompat.Callback() {
    val tag = "MediaSessionCallback"
    val seekPlayBufferTime = 850L

    // on long press events get "collected" for 1000ms, so holding the key down does not
    // fire any events for at least 1000ms. Therefore, after keyup you have to wait
    // at least 1050ms to ensure no long press is being performed
    val shortDelay = 650.milliseconds
    // 650 for unihertz jelly 2e
    // 1100 for Pixel 4a
    val longerDelay = 1100.milliseconds


    var clickPressed = false
    var clickCount = 0
    var lastStatePlaying = false
    var stopSeeking = false
    var clickJob: Job? = null

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onPlay() {
        exoPlayer.play()
    }

    override fun onPause() {
        exoPlayer.pause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
//        exoPlayer.seekTo(pos)
//        _player.value = playerState.value.copy(
//            currentPosition = pos,
//            duration = exoPlayer.duration,
//            isBuffering = exoPlayer.isLoading,
//            isPlaying = exoPlayer.isPlaying,
//            error = null
//        )
    }

    /* CUSTOM sandreas! */
    fun onSeek(offset: Long) {
        val currentPos = exoPlayer.currentPosition
        var newPosition = currentPos + offset
        if (newPosition < 0) {
            newPosition = 0;
        } else if (newPosition > exoPlayer.duration) {
            newPosition = exoPlayer.duration - 1;
        }
        super.onSeekTo(newPosition)
        exoPlayer.seekTo(newPosition)
        /*
        _player.value = playerState.value.copy(
            currentPosition = newPosition,
            duration = exoPlayer.duration
        )

         */
    }





    override fun onMediaButtonEvent(intent: Intent): Boolean {
        if (Intent.ACTION_MEDIA_BUTTON != intent.action) {
            return false;
        }
        val keyEvent = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
        }
        if (keyEvent == null) {
            return false
        }

        // Log.d(tag, "=== debounceKeyEvent: ${keyEventToString(keyEvent)}")
        return debounceKeyEvent(keyEvent)
    }

    private fun keyEventToString(keyEvent: KeyEvent): String {
        var action = ""
        when (keyEvent.action) {
            KeyEvent.ACTION_UP -> {
                action = "ACTION_UP"
            }

            KeyEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
            }
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

            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
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

    private fun debounceKeyEvent(keyEvent: KeyEvent): Boolean {
        Log.d(
            tag,
            "=== debounceKeyEvent: ${keyEventToString(keyEvent)}, clickCount=$clickCount, repeatCount=${keyEvent.repeatCount} longPress=${keyEvent.isLongPress}"
        )

        // how does this work:
        // - every keyDown and keyUp triggers a scheduled handler
        // - another keyDown or keyUp cancels the scheduled handler and re-triggers it with new values
        // - the handler takes clickCount:int and clickPressed:bool (if held down)
        // - keyCodes increase the number of clicks (PlayPause+=1, Next+=2, Prev+=3)
        // - depending on the number of clicks, the playerNotificationService handles the configured action
        // problems:
        // - the logs show pretty accurate click / hold detection, but it does not really translate well in the player
        // - since the trigger is scheduled, it does run in a different thread
        // - this leads to strange behaviour - probably easy to fix, but I'm no kotlin native (Coroutines)
        // - probably after some actions the thread of the player is no longer accessible...
        var timerDelay = shortDelay
        if (keyEvent.action == KeyEvent.ACTION_UP) {
            clickPressed = false
            timerDelay = longerDelay
        } else if (keyEvent.action == KeyEvent.ACTION_DOWN) {
            // if down has already fired without receiving an UP, it is a repeated event
            // that can be ignored
            if (clickPressed) {
                return true
            }

            when (keyEvent.keyCode) {
                KeyEvent.KEYCODE_HEADSETHOOK,
                KeyEvent.KEYCODE_MEDIA_PLAY,
                KeyEvent.KEYCODE_MEDIA_PAUSE,
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                    clickCount++
                    Log.d(
                        tag,
                        "=== handleCallMediaButton: Headset Hook/Play/ Pause, clickCount=$clickCount"
                    )
                }

                KeyEvent.KEYCODE_MEDIA_NEXT -> {
                    clickCount += 2
                    Log.d(tag, "=== handleCallMediaButton: Media Next, clickCount=$clickCount")
                }

                KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                    clickCount += 3
                    Log.d(
                        tag,
                        "=== handleCallMediaButton: Media Previous, clickCount=$clickCount"
                    )
                }

                KeyEvent.KEYCODE_MEDIA_STOP -> {
                    Log.d(tag, "=== handleCallMediaButton: Media Stop, clickCount=$clickCount")
                    onStop()
                    clickJob?.cancel()
                    return true
                }

                else -> {
                    Log.d(tag, "=== KeyCode:${keyEvent.keyCode}, clickCount=$clickCount")
                    return false
                }
            }

            clickPressed = true
        }

        if (clickJob != null) {
            Log.d(
                tag,
                "=== clickTimer cancelled: clicks=$clickCount, hold=$clickPressed ==== ${keyEventToString(keyEvent)} ===="
            )
            clickJob?.cancel()
        }

        // source: https://stackoverflow.com/questions/50858684/kotlin-android-debounce
        clickJob = serviceScope.launch {
            // delay(650);
            Log.d(
                tag,
                "== clickTimer scheduled: delay=${timerDelay.inWholeMilliseconds}ms, clicks=$clickCount, hold=$clickPressed ==== ${keyEventToString(keyEvent)} ===="
            )
            delay(timerDelay)
            handleClicks(clickCount, clickPressed)

            clickCount = 0
        }



        // }


        return true
    }

    fun handleClicks(clicks: Int, clickPressed: Boolean) {
        Log.d(tag, "=== handleClicks: count=$clicks,hold=$clickPressed")
        // return
        stopSeeking = true
        serviceScope.launch {
            // the handlers should be configurlateinitable, defaults:
            // hold -> jumpBackward
            // click -> play / pause
            // click, hold -> fast forward
            // click, click -> next (chapter or track)
            // click, click, hold -> rewind
            // click, click, click -> previous (chapter or track)

            Log.d(tag, "=== handleClicks: count=$clicks,hold=$clickPressed")

            if (clickPressed) {
                lastStatePlaying = exoPlayer.isPlaying
                when (clicks) {
                    1 -> {
                        // jumpBackward()
                        onSeek(-30000)
                    }

                    2 -> {

                        Log.d(tag, "=== fastForward init, stopSeeking=$stopSeeking")
                        stopSeeking = false
                        val mainHandler = Handler(mainLooper)
                        mainHandler.post(object : Runnable {
                            override fun run() {
                                onSeek(10000 - seekPlayBufferTime)
                                onPlay()
                                if(!stopSeeking) {
                                    mainHandler.postDelayed(this, seekPlayBufferTime)
                                }
                            }
                        })
                    }

                    3 -> {
                        Log.d(tag, "=== rewind init, stopSeeking=$stopSeeking")
                        stopSeeking = false
                        val mainHandler = Handler(mainLooper)
                        mainHandler.post(object : Runnable {
                            override fun run() {
                                onSeek(-(10000 + seekPlayBufferTime))
                                onPlay()
                                if(!stopSeeking) {
                                    mainHandler.postDelayed(this, seekPlayBufferTime)
                                }
                            }
                        })
                    }
                }
            } else {
                when (clicks) {
                    0 -> {
                        // switch from fastForward / rewind back to last playing state
                        if (lastStatePlaying) {
                            onPlay()
                        } else {
                            onPause()
                        }
                    }

                    1 -> {
                        if (exoPlayer.isPlaying) {
                            onPause()
                        } else {
                            onPlay()
                        }
                    }

                    2 -> {
                        // todo: implement "next chapter"
                        // workaround: just seek +5mins
                        // skipToNext()
                        // seekForward(300000)
                        onSeek(300000)
                    }

                    3 -> {
                        // todo: implement "previous chapter"
                        // workaround: just seek -5mins
                        // skipToPrevious()
                        // seekBackward(300000)
                        onSeek(-300000)
                    }
                }
            }
        }



    }



}