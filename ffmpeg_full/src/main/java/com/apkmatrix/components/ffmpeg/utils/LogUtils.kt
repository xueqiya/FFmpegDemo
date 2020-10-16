package com.apkmatrix.components.ffmpeg.utils

import android.annotation.SuppressLint
import android.util.Log

@SuppressLint("LongLogTag")
internal object LogUtils {
    var isDebug: Boolean = true
    private const val TAG: String = "FFMPEG_FULL_COMPONENTS_LOG"

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