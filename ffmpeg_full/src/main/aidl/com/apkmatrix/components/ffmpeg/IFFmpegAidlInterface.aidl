// IFFmpegAidlInterface.aidl
package com.apkmatrix.components.ffmpeg;

// Declare any non-default types here with import statements

import com.apkmatrix.components.ffmpeg.IFFmpegListener;
interface IFFmpegAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void register(IFFmpegListener listener);

    void unRegister();
}
