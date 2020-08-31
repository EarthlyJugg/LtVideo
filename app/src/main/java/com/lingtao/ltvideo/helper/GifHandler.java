package com.lingtao.ltvideo.helper;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class GifHandler {
    private long gifAddr;

    public GifHandler(String path) {
        this.gifAddr = loadPath(path);
    }

    private native long loadPath(String path);

    public native int getWidth(long ndkGif);

    public native int getHeight(long ndkGif);

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
}
