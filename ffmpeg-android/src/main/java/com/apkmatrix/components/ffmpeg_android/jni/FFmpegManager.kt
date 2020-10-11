package com.apkmatrix.components.ffmpeg_android.jni

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.apkmatrix.components.ffmpeg_android.FFmpegService
import com.apkmatrix.components.ffmpeg_android.IFFmpegAidlInterface
import com.apkmatrix.components.ffmpeg_android.IFFmpegListener
import com.apkmatrix.components.ffmpeg_android.OnFFmpegListener

object FFmpegManager {
    lateinit var application: Application
    var onFFmpegListener: OnFFmpegListener? = null

    fun init(application: Application) {
        this.application = application
    }

    fun run(context: Context, cmds: String?, onFFmpegListener: OnFFmpegListener?) {
        this.onFFmpegListener = onFFmpegListener
        val intent = Intent(context, FFmpegService::class.java)
        intent.putExtra(FFmpegService.EXTRA_PARAM_CMD, cmds)
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