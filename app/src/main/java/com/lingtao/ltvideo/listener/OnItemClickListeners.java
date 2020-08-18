package com.lingtao.ltvideo.listener;


import com.lingtao.ltvideo.widgets.ViewHolder;

/**
 * Created by cl on 2018/5/3.
 */

public interface OnItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
