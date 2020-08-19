package com.lingtao.ltvideo.service;

import java.io.Serializable;

public interface INetWorkPicture extends Serializable {

    /**
     * 获取网络图片地址
     *
     * @return
     */
    String getNetUrl();

    /**
     * 获取本地图片资源
     *
     * @return
     */
    int getRes();

    /**
     * 回调网络图片的宽
     * @param width
     */
    void onWidth(int width);

    /**
     * 回调网络图片的高
     * @param height
     */
    void onHeight(int height);

}
