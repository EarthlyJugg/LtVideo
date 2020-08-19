package com.lingtao.ltvideo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;

import com.lingtao.ltvideo.bean.GirlItemData;
import com.lingtao.ltvideo.util.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ImageService extends IntentService {

    public ImageService() {
        super("");
    }

    public static void startService(Context context, List<? extends INetWorkPicture> datas, String subtype) {
        Intent intent = new Intent(context, ImageService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        intent.putExtra("subtype", subtype);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<? extends INetWorkPicture> datas = intent.getParcelableArrayListExtra("data");
        String subtype = intent.getStringExtra("subtype");
        handleGirlItemData(datas, subtype);
    }

    private void handleGirlItemData(List<? extends INetWorkPicture> datas, String subtype) {
        if (datas == null || datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        for (INetWorkPicture data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getNetUrl());
            if (bitmap != null) {
                data.onWidth(bitmap.getWidth());
                data.onHeight(bitmap.getHeight());
            }
        }

        EventBus.getDefault().post(datas);
    }
}