package com.apkmatrix.components.ffmpeg_android

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegJni
import com.apkmatrix.components.ffmpeg_android.utils.LogUtils

class FFmpegService : Service() {
    private var listener: IFFmpegListener? = null

    companion object {
        const val EXTRA_PARAM_CMD = "EXTRA_PARAM_CMD"
    }

    override fun onBind(intent: Intent?): IBinder? {
        val cmd = intent?.run { getStringExtra(EXTRA_PARAM_CMD) }
        val split = cmd?.split(" ".toRegex())?.toTypedArray()
        FFmpegJni.run(split, object : FFmpegJni.OnCmdExecListener {
            override fun onSuccess() {
                LogUtils.d("success")
                listener?.onSuccess()
            }

            override fun onFailure() {
                LogUtils.d("onFailure")
                listener?.onFailure()
            }

            override fun onProgress(progress: Float) {
                LogUtils.d("progress$progress")
                listener?.onProgress(progress)
            }
        })
        return mBinder
    }

    private var mBinder = object : IFFmpegAidlInterface.Stub() {
        override fun register(listener: IFFmpegListener?) {
            this@FFmpegService.listener = listener
        }

        override fun unRegister() {

        }
    }
}
