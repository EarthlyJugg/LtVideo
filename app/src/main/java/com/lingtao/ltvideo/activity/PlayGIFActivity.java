package com.lingtao.ltvideo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.adapter.ImageAdapter;
import com.lingtao.ltvideo.giflib.GIfBean;
import com.lingtao.ltvideo.giflib.LtGif;
import com.lingtao.ltvideo.helper.GifHandler;
import com.lingtao.ltvideo.util.LogUtils;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PlayGIFActivity extends AppCompatActivity {

    private static final String TAG = "PlayGIFActivity_log";
    private RecyclerView recyclerView;
    List<GIfBean> list = new LinkedList<>();


    public static void start(Context context) {
        Intent starter = new Intent(context, PlayGIFActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_g_i_f);

        recyclerView = ((RecyclerView) findViewById(R.id.recyclerView));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void playGif(View view) {

        for (int i = 0; i < 15; i++) {
            File file3 = new File(Environment.getExternalStorageDirectory(), "demo3.gif");
            File file4 = new File(Environment.getExternalStorageDirectory(), "demo4.gif");
            list.add(new GIfBean(file3.getAbsolutePath()));
            list.add(new GIfBean(file4.getAbsolutePath()));
        }
        recyclerView.setAdapter(new ImageAdapter(list, this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Iterator<GIfBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            GIfBean bean = iterator.next();
            bean.clear();
            iterator.remove();
            bean = null;
        }
    }
}