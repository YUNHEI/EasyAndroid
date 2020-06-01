package com.chen.baseextend.util

import android.media.AudioManager
import android.media.MediaPlayer

object MediaManager {


    private var isPlaying: Boolean = false

    private var mMediaPlayer: MediaPlayer? = null

    private var path: String? = null

    private var onCompletion: (() -> Unit)? = null

    fun playSound(filePath: String, onCompleted: (() -> Unit)? = null) {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
            isPlaying = false
        }

        mMediaPlayer?.run {
            MediaManager.isPlaying = false
            onCompletion = onCompleted
            try {
//                val attributes = AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .setFlags(AudioAttributes.FLAG_LOW_LATENCY)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
//                        .build()
//                setAudioAttributes(attributes)
                reset()
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                onCompletion?.run {
                    setOnCompletionListener {
                        MediaManager.isPlaying = false
                        invoke()
                    }
                }
                setDataSource(filePath)
                prepare()
                start()
                path = filePath
                MediaManager.isPlaying = true
            } catch (e: Exception) {
                e.printStackTrace()
                MediaManager.isPlaying = false
                onCompletion?.run {
                    setOnCompletionListener {
                        invoke()
                    }
                }
            }
        }
    }

    fun isPlaying(path: String? = null): Boolean {
        path?.run {
            if (path != MediaManager.path) {
                return false
            } else {
                return isPlaying
            }
        } ?: run {
            return isPlaying
        }
    }

    fun stop() {
        if (isPlaying) {
            mMediaPlayer?.stop()
        }
        onCompletion?.invoke()
        isPlaying = false
    }

    fun pause() {
        if (isPlaying) {
            mMediaPlayer?.pause()
            isPlaying = false
        }
    }

    fun resume() {
        if (isPlaying) {
            mMediaPlayer?.start()
            isPlaying = false
        }
    }

//tim
//    fun downloadSoundFile(message: TIMMessage, onSuc: (() -> Unit)? = null) {
//        message.getElement(0)?.run {
//            if (this is TIMSoundElem) {
//                val soundPath = FileUtil.getSoundFilePath(uuid)
//                if (!FileUtil.soundPathExists(soundPath)) {
//                    getSoundToFile(soundPath,
//                            object : TIMValueCallBack<ProgressInfo> {
//
//                                override fun onSuccess(p0: ProgressInfo?) {
//                                }
//
//                                override fun onError(p0: Int, p1: String?) {
//                                }
//                            },
//                            object : TIMCallBack {
//                                override fun onSuccess() {
//                                    onSuc?.invoke()
//                                }
//
//                                override fun onError(p0: Int, p1: String?) {
//                                    File(soundPath).run {
//                                        if (exists()) delete()
//                                    }
//                                }
//                            })
//                }
//            }
//        }
//    }

    fun release() {
        mMediaPlayer?.release()
        mMediaPlayer = null
        isPlaying = false
    }
}
