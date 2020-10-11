package com.apkmatrix.components.ffmpeg_android

interface OnFFmpegListener {
    fun onSuccess()
    fun onFailure()
    fun onProgress(progress: Float)
}