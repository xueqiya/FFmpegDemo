cmake_minimum_required(VERSION 3.4.1)

find_library(log-lib
        log)
message(当前abi:${ANDROID_ABI})

set(CPP_DIR ${CMAKE_SOURCE_DIR}/src/main/cpp)

# armeabi-v7a
if (${ANDROID_ABI} STREQUAL "armeabi-v7a")
    set(LIBS_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/armeabi-v7a)
    set(FDK_AAC_DIR ${CMAKE_SOURCE_DIR}/../ffmpeg4.3.1/fdk-aac/android/armv7-a)
    set(OPENSSL_DIR ${CMAKE_SOURCE_DIR}/../ffmpeg4.3.1/openssl/android/armv7-a)
endif (${ANDROID_ABI} STREQUAL "armeabi-v7a")

# arm64-v8a
if(${ANDROID_ABI} STREQUAL "arm64-v8a")
    set(LIBS_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/arm64-v8a)
    set(FDK_AAC_DIR ${CMAKE_SOURCE_DIR}/../ffmpeg4.3.1/fdk-aac/android/armv8-a)
    set(OPENSSL_DIR ${CMAKE_SOURCE_DIR}/../ffmpeg4.3.1/openssl/android/armv8-a)
endif(${ANDROID_ABI} STREQUAL "arm64-v8a")

include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp)
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/ffmpeg)
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)
include_directories(${CMAKE_SOURCE_DIR}/../ffmpeg4.3.1)

# 添加库
add_library(
        ffmepg_native

        SHARED

        ${CPP_DIR}/ffmepg/cmdutils.c
        ${CPP_DIR}/ffmepg/ffmpeg.c
        ${CPP_DIR}/ffmepg/ffmpeg_filter.c
        ${CPP_DIR}/ffmepg/ffmpeg_opt.c
        ${CPP_DIR}/ffmpeg_thread.c
        ${CPP_DIR}/ffmepg_native.c)

add_library(avcodec
        SHARED
        IMPORTED)
set_target_properties(avcodec
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/libavcodec.so)

add_library(avfilter
        SHARED
        IMPORTED)
set_target_properties(avfilter
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/libavfilter.so)

add_library(avformat
        SHARED
        IMPORTED)
set_target_properties(avformat
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/libavformat.so)

add_library(avutil
        SHARED
        IMPORTED)
set_target_properties(avutil
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/libavutil.so)

add_library(postproc
        SHARED
        IMPORTED)
set_target_properties(postproc
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/libpostproc.so)

add_library(swscale
        SHARED
        IMPORTED)
set_target_properties(swscale
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/libswscale.so)

add_library(fdk-aac
        SHARED
        IMPORTED)
set_target_properties(fdk-aac
        PROPERTIES IMPORTED_LOCATION
        ${FDK_AAC_DIR}/lib/libfdk-aac.so)

add_library(crypto
        SHARED
        IMPORTED)
set_target_properties(crypto
        PROPERTIES IMPORTED_LOCATION
        ${OPENSSL_DIR}/lib/libcrypto.1.1.so)

add_library(ssl
        SHARED
        IMPORTED)
set_target_properties(ssl
        PROPERTIES IMPORTED_LOCATION
        ${OPENSSL_DIR}/lib/libssl.1.1.so)

target_link_libraries(ffmepg_native
        avcodec
        avfilter
        avformat
        avutil
        postproc
        swscale
        fdk-aac
        crypto
        ssl
        ${log-lib})