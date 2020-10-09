package com.apkmatrix.components.ffmpeg_android.jni

import com.apkmatrix.components.ffmpeg_android.utils.LogUtils
import kotlinx.coroutines.*

object FFmpegCmd {
    internal var mainScope = MainScope()
    internal var ioScope = CoroutineScope(Dispatchers.IO + Job())
    private var sOnCmdExecListener: OnCmdExecListener? = null

    fun run(cmds: Array<String>, listener: OnCmdExecListener?) {
        sOnCmdExecListener = listener
        ioScope.launch(CoroutineExceptionHandler { _, exception ->
            LogUtils.e("ffmpeg run error:$exception")
        }) {
            run(cmds.size, cmds)
        }
    }

    fun onExecuted(ret: Int) {
        LogUtils.d("onExecuted:$ret")
        if (sOnCmdExecListener != null) {
            if (ret == 0) {
                sOnCmdExecListener!!.onProgress(100f)
                sOnCmdExecListener!!.onSuccess()
            } else {
                sOnCmdExecListener!!.onFailure()
            }
        }
    }

    fun onProgress(progress: Float) {
        if (sOnCmdExecListener != null) {
            sOnCmdExecListener!!.onProgress(progress)
        }
    }

    private external fun run(argc: Int, argv: Array<String>): Int
    private external fun exit()

    interface OnCmdExecListener {
        fun onSuccess()
        fun onFailure()
        fun onProgress(progress: Float)
    }

    init {
        System.loadLibrary("swscale")
        System.loadLibrary("postproc")
        System.loadLibrary("avutil")
        System.loadLibrary("avformat")
        System.loadLibrary("avfilter")
        System.loadLibrary("avcodec")
        System.loadLibrary("libcrypto.1.1")
        System.loadLibrary("libssl.1.1")
        System.loadLibrary("libfdk-aac")
        System.loadLibrary("ffmepg_native")
    }
}