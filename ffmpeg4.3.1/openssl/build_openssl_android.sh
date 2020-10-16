#!/bin/bash

export NDK=/Users/xueqi/sdk/android/ndk/androidndk20b
export TOOLCHAIN=$NDK/toolchains/llvm/prebuilt/darwin-x86_64

make clean
function build_android
{
    ./Configure  \
    -D__ANDROID_API__=$API \
    --prefix=$PREFIX \
    $COMPILER \
    no-ssl2 \
    no-comp \
    no-asm \
    no-idea \
    no-hw \
    no-engine \
    no-dso \

    make clean
    make -j8
    make install
}

#armev7-a
export ANDROID_NDK_HOME=$NDK
export SYSROOT=$NDK/toolchains/llvm/prebuilt/darwin-x86_64/sysroot
export API=16
export CPU=armv7-a
export COMPILER=android-arm
export PREFIX=$(pwd)/android/$CPU
export PATH=$TOOLCHAIN/bin:$PATH
export CC=$TOOLCHAIN/bin/armv7a-linux-androideabi$API-clang
export CXX=$TOOLCHAIN/bin/armv7a-linux-androideabi$API-clang++
export AR=$TOOLCHAIN/bin/arm-linux-androideabi-ar
export LD=$TOOLCHAIN/bin/arm-linux-androideabi-ld
export AS=$TOOLCHAIN/bin/arm-linux-androideabi-as
export NM=$TOOLCHAIN/bin/arm-linux-androideabi-nm
build_android

##armv8-a
export ANDROID_NDK_HOME=$NDK
export SYSROOT=$NDK/toolchains/llvm/prebuilt/darwin-x86_64/sysroot
export API=21
export CPU=armv8-a
export COMPILER=android-arm64
export PREFIX=$(pwd)/android/$CPU
export PATH=$TOOLCHAIN/bin:$PATH
export CC=$TOOLCHAIN/bin/aarch64-linux-android$API-clang
export CXX=$TOOLCHAIN/bin/aarch64-linux-android$API-clang++
export AR=$TOOLCHAIN/bin/aarch64-linux-android-ar
export LD=$TOOLCHAIN/bin/aarch64-linux-android-ld
export AS=$TOOLCHAIN/bin/aarch64-linux-android-as
export NM=$TOOLCHAIN/bin/aarch64-linux-android-nm
build_android