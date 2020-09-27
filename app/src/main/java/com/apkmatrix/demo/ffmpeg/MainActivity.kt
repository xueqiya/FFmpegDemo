package com.apkmatrix.demo.ffmpeg

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private var separator = File.separator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val path1 = "${separator}storage${separator}emulated${separator}0"
        val path2 = "${getExternalFilesDir(null)?.absolutePath}"
        start.setOnClickListener {
            val cmd = "ffmpeg -y -i $path1/0.mp4 $path2/video_100.gif"
            //val cmd = "ffmpeg -y -i $path1/0.mp4 $path2/1.mp4"
            //val cmd = "ffmpeg -i $path1/0.mp4"
            object : Thread() {
                override fun run() {
                    super.run()
                    // 执行指令
                    val a = cmdRun(cmd)
                    Log.i("执行命令", a.toString() + "")
                }
            }.start()
        }
    }

    private fun cmdRun(command: String): Int {
        val split = command.split(" ".toRegex()).toTypedArray()
        return run(split)
    }

    private external fun run(cmd: Array<String>): Int

    companion object {
        init {
            System.loadLibrary("swscale")
            System.loadLibrary("swresample")
            System.loadLibrary("postproc")
            System.loadLibrary("avutil")
            System.loadLibrary("avformat")
            System.loadLibrary("avfilter")
            System.loadLibrary("avdevice")
            System.loadLibrary("avcodec")
            System.loadLibrary("ffmepg_native")
        }
    }
}