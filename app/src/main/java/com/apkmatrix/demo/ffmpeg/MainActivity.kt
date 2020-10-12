package com.apkmatrix.demo.ffmpeg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apkmatrix.components.ffmpeg_android.OnFFmpegListener
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegManager
import com.apkmatrix.demo.ffmpeg.utils.FileUtils
import com.apkmatrix.demo.ffmpeg.utils.LogUtils
import com.apkmatrix.demo.ffmpeg.utils.OpenFileUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var path1: String
    private lateinit var path2: String
    private var separator = File.separator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FileUtils.copy2Memory(this, "1")
        FileUtils.copy2Memory(this, "2")

        path1 = "$externalCacheDir${separator}1"
        path2 = "${getExternalFilesDir(null)?.absolutePath}"

        start.setOnClickListener {
//            val cmd = "ffmpeg -y -i $path1 -vcodec copy -acodec copy $path2/0.mp4"
//            val cmd = "ffmpeg -y -i $path1 -vcodec copy -acodec copy $path2/0.mp4"
            val cmd = "ffmpeg -y -protocol_whitelist file,http,https,tcp,tls,crypto -i https://gamespotvideo.cbsistatic.com/vr/2020/09/11/517330/GSU_DCFandome2020_DoomPatrol_v2_8000.m3u8 -vcodec copy -acodec copy $path2/0.mp4"
            FFmpegManager.run(this, cmd, object : OnFFmpegListener {
                override fun onSuccess() {
                    LogUtils.d("success")
                }

                override fun onFailure() {
                    LogUtils.d("onFailure")
                }

                override fun onProgress(progress: Float) {
                    LogUtils.d("progress$progress")
                    info.text = progress.toString()
                }
            })
        }

        open.setOnClickListener {
            OpenFileUtil.instance?.openFile(this, File("$path2/0.mp4"))
        }
    }
}