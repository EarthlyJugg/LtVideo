#include <jni.h>
#include <string>

extern "C"{
#include <libavutil/avutil.h>

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_lingtao_ltvideo_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    const char *version = av_version_info();
    std::string hello = "Hello from C++";
    return env->NewStringUTF(version);
}
