package com.apkmatrix.demo.ffmpeg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        run(arrayOf())
    }

    private external fun run(cmd: Array<String>): Int

    companion object {
        init {
            System.loadLibrary("avdevice")
            System.loadLibrary("avutil")
            System.loadLibrary("avcodec")
            System.loadLibrary("swresample")
            System.loadLibrary("avformat")
            System.loadLibrary("swscale")
            System.loadLibrary("avfilter")
            System.loadLibrary("postproc")
            System.loadLibrary("native_lib")
        }
    }
}