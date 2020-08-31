
cmake_minimum_required(VERSION 3.4.1)


file(GLOB source_file src/main/cpp/*.cpp)
file(GLOB source_file2 src/main/cpp/gif/*.c)


add_library(
        native-lib

        SHARED
        ${source_file}
        ${source_file2}
        #        src/main/cpp/native-lib.cpp
)


include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/gif)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/src/main/cpp/libs/${CMAKE_ANDROID_ARCH_ABI} ")


target_link_libraries(
        native-lib

#        avfilter
#        avformat
#        avcodec
#        avutil
#        swresample
#        swscale
        turbojpeg
        jnigraphics
        log
        z
        android
        jnigraphics
        android
        OpenSLES)