package com.lingtao.ltvideo.util;

import android.util.Log;

public class LogUtils {
    private static final String tag = "lingtao";
    private static boolean isPrintf = true;
    public static void d(String content) {
        d(tag, content);
    }

    public static void d(String tag,String content) {
        if (isPrintf) {
            Log.d(tag, content);
        }
    }


    public static void e(String content) {
        e(tag, content);
    }

    public static void e(String tag,String content) {
        if (isPrintf) {
            Log.e(tag, content);
        }
    }


}
