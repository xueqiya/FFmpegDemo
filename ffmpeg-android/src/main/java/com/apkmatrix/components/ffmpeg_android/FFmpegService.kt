package com.apkmatrix.components.ffmpeg_android

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegJni
import com.apkmatrix.components.ffmpeg_android.utils.LogUtils

class FFmpegService : Service() {
    private var listener: IFFmpegListener? = null

    companion object {
        const val EXTRA_PARAM_CMD = "EXTRA_PARAM_CMD"
        const val EXTRA_PARAM_IS_FOREGROUND = "EXTRA_PARAM_IS_FOREGROUND"
    }

    override fun onBind(intent: Intent?): IBinder? {
        val cmd = intent?.getStringExtra(EXTRA_PARAM_CMD)
        val isShowNotify = intent?.getBooleanExtra(EXTRA_PARAM_IS_FOREGROUND, false) ?: false
        if (isShowNotify) {
            startForeground()
        }
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

    @SuppressLint("InlinedApi")
    private fun startForeground() {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("ffmpeg_notification-Id", "ffmpeg_notification-Name")
        } else {
            ""
        }
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setAutoCancel(true)
        startForeground(9999, notification.build())
    }

    @SuppressLint("InlinedApi")
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}
