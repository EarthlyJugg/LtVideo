#include <jni.h>
#include <string>

#include "gif_lib.h"
#include <android/bitmap.h>
#include <malloc.h>
#include "android/log.h"


extern "C" {
#include <libjpeg/jpeglib.h>

}


#define LOGI(...) __android_log_print(ANDROID_LOG_INFO , "(^_^)", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR , "IjkMediaPlayer_log", __VA_ARGS__)

#define  LOG_TAG    "david"
#define  argb(a, r, g, b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

#define  dispose(ext) (((ext)->Bytes[0] & 0x1c) >> 2)
#define  trans_index(ext) ((ext)->Bytes[3])
#define  transparency(ext) ((ext)->Bytes[0] & 1)
#define  delay(ext) (10*((ext)->Bytes[2] << 8 | (ext)->Bytes[1]))
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

typedef struct GifBean {
    int current_frame;
    int total_frame;
    int *dealys;
} GifBean;


extern "C" JNIEXPORT jstring JNICALL
Java_com_lingtao_ltvideo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    const char *version = "4.2.15";
    std::string hello = "Hello from C++";
    return env->NewStringUTF(version);
}

#ifdef open_libjpeg

extern void
writeJpegToFile(uint8_t *temp, int width, int height, jint quality, const char *outPutPath);

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sdldemo_example_activities_VideoActivity_saveBitmapByNative(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jobject bitmap,
                                                                             jint quality,
                                                                             jstring outPutPath_) {

    const char *outPutPath = env->GetStringUTFChars(outPutPath_, 0);

    // 从bitmap中获取argb数据
    AndroidBitmapInfo info;
    // 获取里面的信息
    AndroidBitmap_getInfo(env, bitmap, &info);
    // 得到图片中的像素信息
    uint8_t *pixels;// 相当于byte数组  *pixels === byte[]
    AndroidBitmap_lockPixels(env, bitmap, (void **) &pixels);
    // jpge argb去掉他的a ===> rgb
    // 开一块内存用来存储rgb信息

    if (pixels == NULL) {
        //__android_log_print(ANDROID_LOG_ERROR, "libjpeg_lingtao", "pixels 为空");
        LOGE("pixels 为空");
        return -1;
    }
    LOGE("pixels 不为空");
    // 可以用来存放图片所有信息(w*h为所有像素,每个像素有r,g,b三种颜色)
    uint8_t *data = (uint8_t *) malloc(info.width * info.height * 3);

    uint8_t *temp = data;
    uint8_t r, g, b;
    int color;
    for (int i = 0; i < info.height; ++i) {
        for (int j = 0; j < info.width; ++j) {
            //pixels定义的是无符号字符串类型指针，一个字节
            // pixels 强转成int 类型指针，就变成了四字节，刚好rgba 一个像素就是四个字节
            //所以每次移动一个pixels 就是取到一个像素数据，不然就必须每次一定四位来取到rgba 数据
            color = *(int *) pixels; // color 4个字节,argb

            //rgba 的存储顺序 ABGR
            r = (color >> 16) & 0xFF;
            g = (color >> 8) & 0xFF;
            b = color & 0xFF;
            // 存放颜色,jpeg的格式为bgr,倒过来存
            *data = b;
            *(data + 1) = g;
            *(data + 2) = r;
            data += 3;// 这里就去除了alpha通道
            pixels += 4;// 每个像素有argb4个通道,其中alpha通道被我们丢弃掉了,没有存到data里面
        }

    }
    //把得到的新的图片的信息存入一个新文件 中
    writeJpegToFile(temp, info.width, info.height, quality, outPutPath);
//    writeJpegToFile2(temp, info.width, info.height, quality, outPutPath);

    LOGE("压缩完成:%s", outPutPath);
    free(data);// data是通过malloc开辟的,这里需要释放AndroidBitmap_unlockPixels(env, bitmap);

    env->ReleaseStringUTFChars(outPutPath_, outPutPath);
    return 0;
}

void writeJpegToFile(uint8_t *temp, int width, int height, jint quality, const char *outPutPath) {
//    3.1、创建jpeg压缩对象
    jpeg_compress_struct jcs;
    //错误回调
    jpeg_error_mgr error;
    jcs.err = jpeg_std_error(&error);
    //创建压缩对象
    jpeg_create_compress(&jcs);
//    3.2、指定存储文件  write binary
    FILE *f = fopen(outPutPath, "wb");
    jpeg_stdio_dest(&jcs, f);
//    3.3、设置压缩参数
    jcs.image_width = width;
    jcs.image_height = height;
    //bgr
    jcs.input_components = 3;
    jcs.in_color_space = JCS_RGB;
    jpeg_set_defaults(&jcs);
    //开启哈夫曼功能
    jcs.optimize_coding = true;
    jpeg_set_quality(&jcs, quality, 1);
//    3.4、开始压缩
    jpeg_start_compress(&jcs, 1);
//    3.5、循环写入每一行数据
    int row_stride = width * 3;//一行的字节数
    JSAMPROW row[1];
    while (jcs.next_scanline < jcs.image_height) {
        //取一行数据
        uint8_t *pixels = temp + jcs.next_scanline * row_stride;
        row[0] = pixels;
        jpeg_write_scanlines(&jcs, row, 1);
    }
//    3.6、压缩完成
    jpeg_finish_compress(&jcs);
//    3.7、释放jpeg对象
    fclose(f);
    jpeg_destroy_compress(&jcs);
}

#endif

#define open_load_gif
#ifdef open_load_gif

//void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels) {
int drawFrame(GifFileType *gif, GifBean *gifBean, AndroidBitmapInfo info, void *pixels,
              bool force_dispose_1) {
    GifColorType *bg;
    GifColorType *color;
    SavedImage *frame;
    ExtensionBlock *ext = 0;
    GifImageDesc *frameInfo;
    ColorMapObject *colorMap;
    int *line;
    int width, height, x, y, j, loc, n, inc, p;
    void *px;

    width = gif->SWidth;
    height = gif->SHeight;
    frame = &(gif->SavedImages[gifBean->current_frame]);
    frameInfo = &(frame->ImageDesc);
    if (frameInfo->ColorMap) {
        colorMap = frameInfo->ColorMap;
    } else {
        colorMap = gif->SColorMap;
    }

    bg = &colorMap->Colors[gif->SBackGroundColor];
    for (j = 0; j < frame->ExtensionBlockCount; j++) {
        if (frame->ExtensionBlocks[j].Function == GRAPHICS_EXT_FUNC_CODE) {
            ext = &(frame->ExtensionBlocks[j]);
            break;
        }
    }
    // For dispose = 1, we assume its been drawn
    px = pixels;
    if (ext && dispose(ext) == 1 && force_dispose_1 && gifBean->current_frame > 0) {
        gifBean->current_frame = gifBean->current_frame - 1,
                drawFrame(gif, gifBean, info, pixels, true);
    } else if (ext && dispose(ext) == 2 && bg) {
        for (y = 0; y < height; y++) {
            line = (int *) px;
            for (x = 0; x < width; x++) {
                line[x] = argb(255, bg->Red, bg->Green, bg->Blue);
            }
            px = (int *) ((char *) px + info.stride);
        }
    } else if (ext && dispose(ext) == 3 && gifBean->current_frame > 1) {
        gifBean->current_frame = gifBean->current_frame - 2,
                drawFrame(gif, gifBean, info, pixels, true);
    }
    px = pixels;
    if (frameInfo->Interlace) {
        n = 0;
        inc = 8;
        p = 0;
        px = (int *) ((char *) px + info.stride * frameInfo->Top);
        for (y = frameInfo->Top; y < frameInfo->Top + frameInfo->Height; y++) {
            for (x = frameInfo->Left; x < frameInfo->Left + frameInfo->Width; x++) {
                loc = (y - frameInfo->Top) * frameInfo->Width + (x - frameInfo->Left);
                if (ext && frame->RasterBits[loc] == trans_index(ext) && transparency(ext)) {
                    continue;
                }
                color = (ext && frame->RasterBits[loc] == trans_index(ext)) ? bg
                                                                            : &colorMap->Colors[frame->RasterBits[loc]];
                if (color) {
                    line[x] = argb(255, color->Red, color->Green, color->Blue);
                }
            }
            px = (int *) ((char *) px + info.stride * inc);
            n += inc;
            if (n >= frameInfo->Height) {
                n = 0;
                switch (p) {
                    case 0:
                        px = (int *) ((char *) pixels + info.stride * (4 + frameInfo->Top));
                        inc = 8;
                        p++;
                        break;
                    case 1:
                        px = (int *) ((char *) pixels + info.stride * (2 + frameInfo->Top));
                        inc = 4;
                        p++;
                        break;
                    case 2:
                        px = (int *) ((char *) pixels + info.stride * (1 + frameInfo->Top));
                        inc = 2;
                        p++;
                }
            }
        }
    } else {
        px = (int *) ((char *) px + info.stride * frameInfo->Top);
        for (y = frameInfo->Top; y < frameInfo->Top + frameInfo->Height; y++) {
            line = (int *) px;
            for (x = frameInfo->Left; x < frameInfo->Left + frameInfo->Width; x++) {
                loc = (y - frameInfo->Top) * frameInfo->Width + (x - frameInfo->Left);
                if (ext && frame->RasterBits[loc] == trans_index(ext) && transparency(ext)) {
                    continue;
                }
                color = (ext && frame->RasterBits[loc] == trans_index(ext)) ? bg
                                                                            : &colorMap->Colors[frame->RasterBits[loc]];
                if (color) {
                    line[x] = argb(255, color->Red, color->Green, color->Blue);
                }
            }
            px = (int *) ((char *) px + info.stride);
        }
    }

    return delay(ext);
}
//
////绘制一张图片
//void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels) {
//    //播放底层代码
////        拿到当前帧
//    SavedImage savedImage = gifFileType->SavedImages[gifBean->current_frame];
//
//    GifImageDesc frameInfo = savedImage.ImageDesc;
//    //整幅图片的首地址
//    int* px = (int *)pixels;
////    每一行的首地址
//    int *line;
//
////   其中一个像素的位置  不是指针  在颜色表中的索引
//    int  pointPixel;
//    GifByteType  gifByteType;
//    GifColorType gifColorType;
//    ColorMapObject* colorMapObject=frameInfo.ColorMap;
//    px = (int *) ((char*)px + info.stride * frameInfo.Top);
//    for (int y =frameInfo.Top; y < frameInfo.Top+frameInfo.Height; ++y) {
//        line=px;
//        for (int x = frameInfo.Left; x< frameInfo.Left + frameInfo.Width; ++x) {
//            pointPixel = (y - frameInfo.Top) * frameInfo.Width + (x - frameInfo.Left);
//            gifByteType = savedImage.RasterBits[pointPixel];
//            gifColorType = colorMapObject->Colors[gifByteType];
//            line[x] = argb(255,gifColorType.Red, gifColorType.Green, gifColorType.Blue);
//        }
//        px = (int *) ((char*)px + info.stride);
//    }
//
//}
#endif

extern "C"
JNIEXPORT jlong JNICALL
Java_com_lingtao_ltvideo_helper_GifHandler_loadPath(JNIEnv *env, jobject thiz, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    int err;
//用系统函数打开一个gif文件   返回一个结构体，这个结构体为句柄
    GifFileType *gifFileType = DGifOpenFileName(path, &err);

    DGifSlurp(gifFileType);
    GifBean *gifBean = (GifBean *) malloc(sizeof(GifBean));


//    清空内存地址
    memset(gifBean, 0, sizeof(GifBean));
    gifFileType->UserData = gifBean;

    gifBean->dealys = (int *) malloc(sizeof(int) * gifFileType->ImageCount);
    memset(gifBean->dealys, 0, sizeof(int) * gifFileType->ImageCount);
    gifBean->total_frame = gifFileType->ImageCount;
    ExtensionBlock *ext;
    for (int i = 0; i < gifFileType->ImageCount; ++i) {
        SavedImage frame = gifFileType->SavedImages[i];
        for (int j = 0; j < frame.ExtensionBlockCount; ++j) {
            if (frame.ExtensionBlocks[j].Function == GRAPHICS_EXT_FUNC_CODE) {
                ext = &frame.ExtensionBlocks[j];
                break;
            }
        }
        if (ext) {
            int frame_delay = 10 * (ext->Bytes[2] << 8 | ext->Bytes[1]);
            LOGE("时间  %d   ", frame_delay);
            gifBean->dealys[i] = frame_delay;

        }
    }
    LOGE("gif  长度大小    %d  ", gifFileType->ImageCount);
    env->ReleaseStringUTFChars(path_, path);
    return (jlong) gifFileType;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_lingtao_ltvideo_helper_GifHandler_getWidth(JNIEnv *env, jobject thiz, jlong ndkGif) {
    GifFileType *gifFileType = (GifFileType *) ndkGif;
    return gifFileType->SWidth;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_lingtao_ltvideo_helper_GifHandler_getHeight(JNIEnv *env, jobject thiz, jlong ndkGif) {
    GifFileType *gifFileType = (GifFileType *) ndkGif;
    return gifFileType->SHeight;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_lingtao_ltvideo_helper_GifHandler_updateFrame(JNIEnv *env, jobject thiz, jlong ndkGif,
                                                       jobject bitmap) {

    //强转代表gif图片的结构体
    GifFileType *gifFileType = (GifFileType *) ndkGif;
    GifBean *gifBean = (GifBean *) gifFileType->UserData;
    AndroidBitmapInfo info;
    //代表一幅图片的像素数组
    void *pixels;
    AndroidBitmap_getInfo(env, bitmap, &info);
    //锁定bitmap  一幅图片--》二维 数组   ===一个二维数组
    AndroidBitmap_lockPixels(env, bitmap, &pixels);

    // TODO
    drawFrame(gifFileType, gifBean, info, pixels, false);

    //播放完成之后   循环到下一帧
    gifBean->current_frame += 1;
    LOGE("当前帧  %d  ", gifBean->current_frame);
    if (gifBean->current_frame >= gifBean->total_frame - 1) {
        gifBean->current_frame = 0;
        LOGE("重新过来  %d  ", gifBean->current_frame);
    }
    AndroidBitmap_unlockPixels(env, bitmap);
    return gifBean->dealys[gifBean->current_frame];
}