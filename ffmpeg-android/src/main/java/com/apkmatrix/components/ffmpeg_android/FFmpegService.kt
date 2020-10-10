package com.apkmatrix.components.ffmpeg_android

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegJni
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegManager

class FFmpegService : Service() {
    companion object {
        const val EXTRA_PARAM_CMD = "EXTRA_PARAM_CMD"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val cmd = intent?.run { getStringExtra(EXTRA_PARAM_CMD) }
        val split = cmd?.split(" ".toRegex())?.toTypedArray()
        val fFmpegJni = FFmpegJni()
        fFmpegJni.run(split, FFmpegManager.cmdExecListener)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
