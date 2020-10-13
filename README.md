# ffmpeg

[最新版引用地址](http://maven.302e.com:3080/#browse/browse:maven-snapshots:com%2Fapkmatrix%2Fcomponents%2Fffmpeg)

## I. 如何引入
###### 1. 依赖
```
maven { url 'http://maven.302e.com:3080/repository/maven-public/' }

implementation 'com.apkmatrix.components:ffmpeg::{版本号}'
```
###### 2. 混淆
```
```
## II. 如何使用
###### 1. 初始化
可在任意位置初始化，VideoDL.isInit()判断是否已初始化
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