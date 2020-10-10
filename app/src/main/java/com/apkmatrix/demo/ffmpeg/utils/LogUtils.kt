package com.apkmatrix.demo.ffmpeg.utils

import android.util.Log

object LogUtils {
    var isDebug: Boolean = true
    private const val TAG: String = "FFMPEG_DEMO_LOG"

    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }
}