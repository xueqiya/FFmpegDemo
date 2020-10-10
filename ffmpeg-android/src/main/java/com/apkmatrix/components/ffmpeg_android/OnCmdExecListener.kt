package com.apkmatrix.components.ffmpeg_android

interface OnCmdExecListener {
    fun onSuccess()
    fun onFailure()
    fun onProgress(progress: Float)
}