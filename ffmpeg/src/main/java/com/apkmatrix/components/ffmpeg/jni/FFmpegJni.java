package com.apkmatrix.components.ffmpeg.jni;

import com.apkmatrix.components.ffmpeg.utils.LogUtils;

public class FFmpegJni {
    private static OnCmdExecListener onCmdExecListener;

    public static void run(String[] cmds, OnCmdExecListener listener) {
        onCmdExecListener = listener;
        if (cmds.length == 0) return;
        new Thread() {
            @Override
            public void run() {
                FFmpegJni.run(cmds.length, cmds);
            }
        }.start();
    }

    public static void onExecuted(int ret) {
        if (onCmdExecListener != null) {
            if (ret == 0) {
                LogUtils.d("onSuccess" + ret);
                onCmdExecListener.onProgress(100f);
                onCmdExecListener.onSuccess();
            } else {
                LogUtils.d("onFailure" + ret);
                onCmdExecListener.onFailure();
            }
        }
    }

    public static void onProgress(float progress) {
        LogUtils.d("onProgress:" + progress);
        if (onCmdExecListener != null) {
            onCmdExecListener.onProgress(progress);
        }
    }

    public interface OnCmdExecListener {
        void onSuccess();

        void onFailure();

        void onProgress(float progress);
    }

    private static native int run(int argc, String[] argv);

    static {
        System.loadLibrary("swscale");
        System.loadLibrary("postproc");
        System.loadLibrary("avutil");
        System.loadLibrary("avformat");
        System.loadLibrary("avfilter");
        System.loadLibrary("avcodec");
        System.loadLibrary("crypto.1.1");
        System.loadLibrary("ssl.1.1");
        System.loadLibrary("fdk-aac");
        System.loadLibrary("ffmepg_native");
    }
}