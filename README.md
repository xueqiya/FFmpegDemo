# ffmpeg

###### 1. 初始化
```
FFmpegManager.init(this)
```
###### 2.使用
```
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
```
## III. 其他
- 多进程可能会导致application走多次或者静态常量失效
- ffmpeg支持模块查看ffmpeg/build_android.sh编译配置文件