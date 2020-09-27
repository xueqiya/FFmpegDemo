#include <jni.h>
#include "ffmepg/ffmpeg.h"

JNIEXPORT jint

Java_com_apkmatrix_demo_ffmpeg_MainActivity_run(JNIEnv *env, jobject obj, jobjectArray commands) {
    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];

    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring)(*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env, js, 0);
    }
    return run(argc, argv);
}