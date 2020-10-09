#!/bin/bash

make clean
export TMPDIR=$(pwd)/ffmpegtemp
export NDK=/Users/xueqi/sdk/android/ndk/androidndk20b
export TOOLCHAIN=$NDK/toolchains/llvm/prebuilt/darwin-x86_64

function build_android
{
echo "Compiling FFmpeg for $CPU"
./configure \
    --prefix=$PREFIX \
    --extra-cflags="-I/$(pwd)/fdk-aac/android/${CPU}/include" \
    --extra-ldflags="-L/$(pwd)/fdk-aac/android/${CPU}/lib" \
    --extra-cflags="-I/$(pwd)/openssl/android/${CPU}/include" \
    --extra-ldflags="-L/$(pwd)/openssl/android/${CPU}/lib" \
    --extra-cflags="-Os -fpic $OPTIMIZE_CFLAGS" \
    --extra-ldflags="$ADDI_LDFLAGS" \
    --disable-static \
    --enable-shared \
    --disable-debug \
    --enable-cross-compile \
    --enable-small \
    --enable-gpl \
    --enable-nonfree \
    \
    --disable-ffmpeg \
    --disable-ffplay \
    --disable-ffprobe \
    --disable-avdevice \
    --disable-programs \
    --disable-swresample \
    --disable-doc \
    --disable-runtime-cpudetect \
    --disable-htmlpages \
    --disable-htmlpages \
    --disable-manpages \
    --disable-podpages \
    --disable-txtpages \
    --disable-everything \
    --enable-network \
    --enable-openssl \
    --enable-jni \
    --enable-mediacodec \
    --enable-decoder=h264_mediacodec \
    --enable-demuxers \
    --enable-muxer=mp4 \
    --enable-protocols \
    --enable-protocol=https \
    \
    --enable-libfdk_aac \
    --enable-encoder=libfdk_aac \
    --enable-decoder=aac_latm \
    --enable-decoder=aac \
    --enable-demuxer=aac \
    --enable-parser=aac \
    \
    --enable-libvpx \
    --enable-decoder=vp8 \
    --enable-parser=vp8 \
    --enable-decoder=vp9 \
    --enable-parser=vp9 \
    \
    --enable-decoder=mjpeg \
    --enable-demuxer=mjpeg \
    --enable-parser=mjpeg \
    \
    --enable-decoder=png \
    --enable-parser=png \
    \
    --enable-decoder=h264 \
    --enable-demuxer=h264 \
    --enable-parser=h264 \
    \
    --cross-prefix=$CROSS_PREFIX \
    --target-os=android \
    --arch=$ARCH \
    --cpu=$CPU \
    --cc=$CC \
    --cxx=$CXX \
    --nm=$NM \
    --strip=$STRIP \
    --sysroot=$SYSROOT

make -j8
make install
echo "The Compilation of FFmpeg for $CPU is completed"
}

#armv7-a
API=16
ARCH=arm
CPU=armv7-a
CC=$TOOLCHAIN/bin/armv7a-linux-androideabi$API-clang
CXX=$TOOLCHAIN/bin/armv7a-linux-androideabi$API-clang++
NM=$TOOLCHAIN/bin/arm-linux-androideabi-nm
STRIP=$TOOLCHAIN/bin/arm-linux-androideabi-strip
SYSROOT=$NDK/toolchains/llvm/prebuilt/darwin-x86_64/sysroot
CROSS_PREFIX=$TOOLCHAIN/bin/arm-linux-androideabi-
PREFIX=$(pwd)/android/$CPU
OPTIMIZE_CFLAGS="-mfloat-abi=softfp -mfpu=vfp -marm -march=$CPU "
build_android

#armv8-a
#API=21
#ARCH=arm64
#CPU=armv8-a
#CC=$TOOLCHAIN/bin/aarch64-linux-android$API-clang
#CXX=$TOOLCHAIN/bin/aarch64-linux-android$API-clang++
#NM=$TOOLCHAIN/bin/aarch64-linux-android-nm
#STRIP=$TOOLCHAIN/bin/aarch64-linux-android-strip
#SYSROOT=$NDK/toolchains/llvm/prebuilt/darwin-x86_64/sysroot
#CROSS_PREFIX=$TOOLCHAIN/bin/aarch64-linux-androideabi-
#PREFIX=$(pwd)/android/$CPU
#OPTIMIZE_CFLAGS="-mfloat-abi=softfp -mfpu=vfp -marm -march=$CPU "
#build_android