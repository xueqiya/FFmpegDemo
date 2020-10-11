package com.apkmatrix.components.ffmpeg_android.utils

import android.util.Log

internal object LogUtils {
    var isDebug: Boolean = true
    private const val TAG: String = "FFMPEG_DEMO_LOG"

    @JvmStatic
    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    @JvmStatic
    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    @JvmStatic
    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }
}