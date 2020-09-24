package com.apkmatrix.demo.ffmpeg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        run(0, arrayOf())
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private external fun run(cmdLen: Int, cmd: Array<String>): Int

    companion object {
        // Used to load the 'native-lib' library on application startup.
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
