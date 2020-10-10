package com.apkmatrix.components.ffmpeg_android.jni

import android.content.Context
import android.content.Intent
import com.apkmatrix.components.ffmpeg_android.FFmpegService
import com.apkmatrix.components.ffmpeg_android.OnCmdExecListener
import com.apkmatrix.components.ffmpeg_android.utils.LogUtils

object FFmpegManager {
    fun run(context: Context, cmds: String?) {
        val intent = Intent(context, FFmpegService::class.java)
        intent.putExtra(FFmpegService.EXTRA_PARAM_CMD, cmds)
        context.startService(intent)
    }

    val cmdExecListener = object : OnCmdExecListener {
        override fun onSuccess() {
            LogUtils.d("success")
        }

        override fun onFailure() {
            LogUtils.d("onFailure")
        }

        override fun onProgress(progress: Float) {
            LogUtils.d("progress$progress")
            //info.text = progress.toString()
        }
    }
}