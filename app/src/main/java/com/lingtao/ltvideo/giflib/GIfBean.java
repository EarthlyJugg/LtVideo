package com.lingtao.ltvideo.giflib;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.lingtao.ltvideo.helper.GifHandler;

import java.util.Objects;

public class GIfBean {

    //图片地址
    private String path;

    private long gifAddr;

    private int width;

    private int height;

    private Bitmap bitmap;

    private GifHandler gifHandler;

    private ImageView imageView;

    private boolean isPlayer = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mNextFrame = gifHandler.updateFrame(bitmap);
            imageView.setImageBitmap(bitmap);
            handler.sendEmptyMessageDelayed(1, mNextFrame);
        }
    };

    public GIfBean(String path) {

        this.path = path;
        gifHandler = new GifHandler(path);
        this.gifAddr = gifHandler.getGifAddr();
        this.width = gifHandler.getWidth();
        this.height = gifHandler.getHeight();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void showImage(ImageView imageView) {
        this.imageView = imageView;
        gifHandler.updateFrame(bitmap);
        imageView.setImageBitmap(bitmap);
    }

    public void pause() {
        if (handler.hasMessages(1)) {
            handler.removeMessages(1);
        }
        isPlayer = false;
    }

    public void start(ImageView imageView) {
        this.imageView = imageView;
        if (handler.hasMessages(1)) {
            return;
        }
        isPlayer = true;
        handler.sendEmptyMessage(1);
    }

    public void clear() {
        gifHandler.clear();
        gifHandler = null;
        bitmap.recycle();
        bitmap = null;
        handler.removeMessages(1);
    }


//    @Override
//    public boolean equals(Object o) {
//
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GIfBean gIfBean = (GIfBean) o;
//        return gifAddr == gIfBean.gifAddr;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(gifAddr);
//    }
}
