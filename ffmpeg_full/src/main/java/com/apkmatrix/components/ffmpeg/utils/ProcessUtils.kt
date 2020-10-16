package com.apkmatrix.components.ffmpeg.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Process

internal object ProcessUtils {
    fun getCurrentProcessName(context: Context): String? {
        val pid = Process.myPid()
        val mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (!mActivityManager.runningAppProcesses.isNullOrEmpty()) {
            for (appProcess in mActivityManager.runningAppProcesses) {
                if (appProcess.pid == pid) {
                    return appProcess.processName
                }
            }
        }
        return null
    }
}