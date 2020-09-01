package com.lingtao.ltvideo.helper;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class GifHandler {

    static {
        System.loadLibrary("native-lib");
    }

    private long gifAddr;

    public GifHandler(String path) {
        this.gifAddr = loadPath(path);
    }

    public long getGifAddr() {
        return gifAddr;
    }

    private native long loadPath(String path);

    public native int getWidth(long ndkGif);

    public native int getHeight(long ndkGif);

    public native void clear(long ndkGif);

    public native int updateFrame(long ndkGif, Bitmap bitmap);

    public int getWidth() {
        return getWidth(gifAddr);
    }

    public int getHeight() {
        return getHeight(gifAddr);
    }

    public int updateFrame(Bitmap bitmap) {
        return updateFrame(gifAddr, bitmap);
    }

    public void clear() {
        clear(gifAddr);
    }
}
