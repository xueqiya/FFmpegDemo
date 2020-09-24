cmake_minimum_required(VERSION 3.4.1)

find_library(log-lib
        log)

# 定义变量
set(prebuilt_DIR ${CMAKE_CURRENT_LIST_DIR}/prebuilt)

# 引入头文件
include_directories(${CMAKE_CURRENT_LIST_DIR}/includes)

# 添加库——自己编写的库
# 库名称：native-lib
# 库类型：SHARED，表示动态库，后缀为.so（如果是STATIC，则表示静态库，后缀为.a）
# 库源码文件：src/main/cpp/native-lib.cpp
add_library(native_lib
        SHARED
        include/native_lib.cpp)

# 添加库——外部引入的库
# 库名称：avcodec（不需要包含前缀lib）
# 库类型：SHARED，表示动态库，后缀为.so（如果是STATIC，则表示静态库，后缀为.a）
# IMPORTED表明是外部引入的库
add_library(avcodec
        SHARED
        IMPORTED)
# 设置目标属性
# 设置avcodec目标库的IMPORTED_LOCATION属性，用于说明引入库的位置
# 还可以设置其他属性，格式：PROPERTIES key value
set_target_properties(avcodec
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libavcodec.so)

add_library(avdevice
        SHARED
        IMPORTED)
set_target_properties(avdevice
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libavdevice.so)

add_library(avfilter
        SHARED
        IMPORTED)
set_target_properties(avfilter
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libavfilter.so)

add_library(avformat
        SHARED
        IMPORTED)
set_target_properties(avformat
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libavformat.so)

add_library(avutil
        SHARED
        IMPORTED)
set_target_properties(avutil
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libavutil.so)

add_library(postproc
        SHARED
        IMPORTED)
set_target_properties(postproc
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libpostproc.so)

add_library(swresample
        SHARED
        IMPORTED)
set_target_properties(swresample
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libswresample.so)

add_library(swscale
        SHARED
        IMPORTED)
set_target_properties(swscale
        PROPERTIES IMPORTED_LOCATION
        ${prebuilt_DIR}/libswscale.so)

# 告诉编译器生成native-lib库需要链接的库
# native-lib库需要依赖avcodec、avfilter等库
target_link_libraries(native_lib
        avcodec
        avdevice
        avfilter
        avformat
        avutil
        postproc
        swresample
        swscale
        ${log-lib})