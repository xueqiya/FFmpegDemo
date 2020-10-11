// IFFmpegListener.aidl
package com.apkmatrix.components.ffmpeg_android;

// Declare any non-default types here with import statements

interface IFFmpegListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onSuccess();

    void onFailure();

    void onProgress(float progress);
}
