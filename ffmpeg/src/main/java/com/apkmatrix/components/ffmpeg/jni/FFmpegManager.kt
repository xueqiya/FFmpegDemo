package com.apkmatrix.components.ffmpeg.jni

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.os.RemoteException
import android.text.TextUtils
import com.apkmatrix.components.ffmpeg.FFmpegService
import com.apkmatrix.components.ffmpeg.IFFmpegAidlInterface
import com.apkmatrix.components.ffmpeg.IFFmpegListener
import com.apkmatrix.components.ffmpeg.OnFFmpegListener
import com.apkmatrix.components.ffmpeg.utils.ActivityManager
import com.apkmatrix.components.ffmpeg.utils.ProcessUtils

object FFmpegManager {
    lateinit var application: Application
    var onFFmpegListener: OnFFmpegListener? = null

    fun init(application: Application) {
        val currentProcess = ProcessUtils.getCurrentProcessName(application)
        val packageName = application.packageName
        if (TextUtils.equals(currentProcess, packageName)) {
            this.application = application
            ActivityManager.initial(application)
        }
    }

    fun run(cmds: String?, onFFmpegListener: OnFFmpegListener?) {
        this.onFFmpegListener = onFFmpegListener
        val intent = Intent(application, FFmpegService::class.java)
        intent.putExtra(FFmpegService.EXTRA_PARAM_CMD, cmds)
        if (ActivityManager.instance.isBackground && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.putExtra(FFmpegService.EXTRA_PARAM_IS_FOREGROUND, true)
        } else {
            intent.putExtra(FFmpegService.EXTRA_PARAM_IS_FOREGROUND, false)
        }
        application.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val iFFmpegAidlInterface = IFFmpegAidlInterface.Stub.asInterface(p1)
            try {
                iFFmpegAidlInterface.register(listener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    private val listener: IFFmpegListener = object : IFFmpegListener.Stub() {
        override fun onSuccess() {
            onFFmpegListener?.onSuccess()
            application.unbindService(mServiceConnection)
        }

        override fun onFailure() {
            onFFmpegListener?.onFailure()
            application.unbindService(mServiceConnection)
        }

        override fun onProgress(progress: Float) {
            onFFmpegListener?.onProgress(progress)
        }
    }
}