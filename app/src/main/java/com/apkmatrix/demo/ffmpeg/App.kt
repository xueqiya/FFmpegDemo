package com.apkmatrix.demo.ffmpeg

import android.app.Application
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FFmpegManager.init(this)
    }
}