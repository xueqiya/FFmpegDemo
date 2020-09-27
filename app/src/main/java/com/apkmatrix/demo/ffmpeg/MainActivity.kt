package com.apkmatrix.demo.ffmpeg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var path1: String
    private lateinit var path2: String
    private var separator = File.separator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FileUtils.copy2Memory(this, "0.mp4")

        path1 = "$externalCacheDir${separator}0.mp4"
        path2 = "${getExternalFilesDir(null)?.absolutePath}${separator}0.gif"

        start.setOnClickListener {
            val cmd = "ffmpeg -y -i $path1 $path2"
            //val cmd = "ffmpeg -y -i $path1/0.mp4 $path2/1.mp4"
            //val cmd = "ffmpeg -i $path1/0.mp4"
            object : Thread() {
                override fun run() {
                    super.run()
                    // 执行指令
                    LogUtils.d("执行命令$cmd")
                    cmdRun(cmd)
                }
            }.start()
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