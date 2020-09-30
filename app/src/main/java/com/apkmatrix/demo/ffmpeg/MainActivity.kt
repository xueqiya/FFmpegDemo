package com.apkmatrix.demo.ffmpeg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apkmatrix.components.ffmpeg_android.jni.FFmpegCmd
import com.apkmatrix.demo.ffmpeg.utils.FileUtils
import com.apkmatrix.demo.ffmpeg.utils.LogUtils
import com.apkmatrix.demo.ffmpeg.utils.OpenFileUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var path1: String
    private lateinit var path2: String
    private var separator = File.separator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FileUtils.copy2Memory(this, "1")

        path1 = "$externalCacheDir${separator}1"
        path2 = "${getExternalFilesDir(null)?.absolutePath}"

        start.setOnClickListener {
//            val cmd = "ffmpeg -y -i $path1 -c copy $path2/0.h264"
//            val cmd = "ffmpeg -y -i $path1 $path2/0.gif"
//            val cmd = "ffmpeg -i $path1/0.mp4"
            val cmd = "ffmpeg -i 'https://gamespotvideo.cbsistatic.com/vr/2020/09/11/517330/GSU_DCFandome2020_DoomPatrol_v2_8000.m3u8' $path1/0.avi"
            thread {
                cmdRun(cmd)
            }
        }

        open.setOnClickListener {
            OpenFileUtil.instance?.openFile(this, File(path2))
        }
    }

    private fun cmdRun(command: String) {
        val split = command.split(" ".toRegex()).toTypedArray()
        return FFmpegCmd.run(split, object : FFmpegCmd.OnCmdExecListener {
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
}