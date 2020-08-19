package com.lingtao.ltvideo.listener;

import android.util.Log;

import java.util.List;

public interface OnLoadImageSizeListener<T> {

    /**
     * progresse 完成的进度，使用在后面加% 显示就可以
     * @param progresse
     */
    default void onProgressUpdate(int progresse) {

    }


    void onLoadFinish(T t);

}
