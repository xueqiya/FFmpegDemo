package com.apkmatrix.components.ffmpeg_android.jni;

public class FFmpegCmd {
    static {
        System.loadLibrary("swscale");
        System.loadLibrary("postproc");
        System.loadLibrary("avutil");
        System.loadLibrary("avformat");
        System.loadLibrary("avfilter");
        System.loadLibrary("avcodec");
        System.loadLibrary("ffmepg_native");
    }

    private static OnCmdExecListener sOnCmdExecListener;

    public static void run(String[] cmds, OnCmdExecListener listener) {
        sOnCmdExecListener = listener;
        run(cmds.length, cmds);
    }

    public static void onExecuted(int ret) {
        if (sOnCmdExecListener != null) {
            if (ret == 0) {
                sOnCmdExecListener.onProgress(100);
                sOnCmdExecListener.onSuccess();
            } else {
                sOnCmdExecListener.onFailure();
            }
        }
    }

    public static void onProgress(float progress) {
        if (sOnCmdExecListener != null) {
            sOnCmdExecListener.onProgress(progress);
        }
    }

    public interface OnCmdExecListener {
        void onSuccess();

        void onFailure();

        void onProgress(float progress);
    }

    private static native int run(int argc, String[] argv);

    public static native void exit();
}
