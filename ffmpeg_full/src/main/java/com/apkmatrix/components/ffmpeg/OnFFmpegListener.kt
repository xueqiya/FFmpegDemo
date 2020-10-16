package com.apkmatrix.components.ffmpeg

interface OnFFmpegListener {
    fun onSuccess()
    fun onFailure()
    fun onProgress(progress: Float)
}