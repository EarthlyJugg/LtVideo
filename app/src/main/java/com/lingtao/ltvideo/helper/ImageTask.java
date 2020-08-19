package com.lingtao.ltvideo.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.lingtao.ltvideo.listener.OnLoadImageSizeListener;
import com.lingtao.ltvideo.service.INetWorkPicture;
import com.lingtao.ltvideo.util.ImageLoader;

import java.util.List;

/**
 * ImageTask 是一次性的，在加载完成或者调用 stop 方法，都会把传进来的context 和 onLoadImageSizeListener 置空
 */
public class ImageTask extends AsyncTask<List<? extends INetWorkPicture>, Integer, List<? extends INetWorkPicture>> {

    private static final String TAG = "ImageTask_log";
    private boolean isRunning = false;
    private boolean stop = false;
    private int total = 0;
    private OnLoadImageSizeListener<List<? extends INetWorkPicture>> onLoadImageSizeListener;

    private Context context;

    public ImageTask(Context context, OnLoadImageSizeListener<List<? extends INetWorkPicture>> onLoadImageSizeListener) {
        this.context = context;
        this.onLoadImageSizeListener = onLoadImageSizeListener;
    }

    @Override
    protected void onPreExecute() {
        isRunning = true;
    }


    @Override
    protected List<? extends INetWorkPicture> doInBackground(List<? extends INetWorkPicture>... lists) {

        int count = 0;
        List<? extends INetWorkPicture> list = lists[0];
        total = list.size();
        if (total == 0) {
            return null;
        }

        for (INetWorkPicture data : list) {
            if (stop) {
                return null;
            }

            Bitmap bitmap = ImageLoader.load(context, data.getNetUrl());
            if (bitmap != null) {
                data.onWidth(bitmap.getWidth());
                data.onHeight(bitmap.getHeight());
            }
            count++;
            publishProgress(count);
        }
        return list;
    }


    @Override
    protected void onProgressUpdate(Integer... progresses) {
        if (total == 0) {
            return;
        }
        float num = (float) progresses[0];
        float v = num / total;
        if (onLoadImageSizeListener != null) {
            onLoadImageSizeListener.onProgressUpdate((int) (v * 100));
        }

    }

    @Override
    protected void onPostExecute(List<? extends INetWorkPicture> result) {
        isRunning = false;
        context = null;
        total = 0;
        if (stop) {
            onLoadImageSizeListener = null;
            return;
        }

        if (onLoadImageSizeListener != null) {
            onLoadImageSizeListener.onLoadFinish(result);
        }
        onLoadImageSizeListener = null;

    }


    @Override
    protected void onCancelled() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        stop = true;
    }


}
