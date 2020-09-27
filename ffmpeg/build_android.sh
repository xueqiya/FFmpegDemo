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
    --disable-static \
    --disable-doc \
    --disable-ffmpeg \
    --disable-ffplay \
    --disable-ffprobe \
    --disable-symver \
    --disable-runtime-cpudetect \
    --disable-htmlpages \
    --disable-htmlpages \
    --disable-manpages \
    --disable-podpages \
    --disable-txtpages \
    --enable-shared \
    --enable-jni \
    --enable-gpl \
    --enable-neon \
    --enable-hwaccels \
    --enable-avdevice \
    --enable-postproc \
    --enable-mediacodec \
    --enable-decoder=h264_mediacodec \
    --enable-nonfree \
    --enable-cross-compile \
    --enable-small \
    --cross-prefix=$CROSS_PREFIX \
    --target-os=android \
    --arch=$ARCH \
    --cpu=$CPU \
    --cc=$CC \
    --cxx=$CXX \
    --nm=$NM \
    --strip=$STRIP \
    --sysroot=$SYSROOT \
    --extra-cflags="-Os -fpic $OPTIMIZE_CFLAGS" \
    --extra-ldflags="$ADDI_LDFLAGS" \
    $ADDITIONAL_CONFIGURE_FLAG

make clean
make -j32
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
API=21
ARCH=arm64
CPU=armv8-a
CC=$TOOLCHAIN/bin/aarch64-linux-android$API-clang
CXX=$TOOLCHAIN/bin/aarch64-linux-android$API-clang++
NM=$TOOLCHAIN/bin/aarch64-linux-android-nm
STRIP=$TOOLCHAIN/bin/aarch64-linux-android-strip
SYSROOT=$NDK/toolchains/llvm/prebuilt/darwin-x86_64/sysroot
CROSS_PREFIX=$TOOLCHAIN/bin/aarch64-linux-androideabi-
PREFIX=$(pwd)/android/$CPU
OPTIMIZE_CFLAGS="-mfloat-abi=softfp -mfpu=vfp -marm -march=$CPU "
build_android