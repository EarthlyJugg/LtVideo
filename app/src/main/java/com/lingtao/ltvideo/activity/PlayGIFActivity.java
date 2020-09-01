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
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

//        for (int i = 0; i < 15; i++) {
//            File file3 = new File(Environment.getExternalStorageDirectory(), "demo3.gif");
//            File file4 = new File(Environment.getExternalStorageDirectory(), "demo4.gif");
//            list.add(new GIfBean(file3.getAbsolutePath()));
//            list.add(new GIfBean(file4.getAbsolutePath()));
//        }
//        recyclerView.setAdapter(new ImageAdapter(list, this));


        OkHttpClient client = new OkHttpClient.Builder().build();
        File mFile = new File(Environment.getExternalStorageDirectory(), "demo3.gif");
        File mFile2 = new File(Environment.getExternalStorageDirectory(), "demo4.gif");

        // 文件上传的请求体封装
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
//                .addFormDataPart("key", "abc")
//                .addFormDataPart("file1", mFile.getName(), RequestBody.create(MediaType.parse("image/jpg"), mFile))
                .addFormDataPart("file2", mFile2.getName(), RequestBody.create(MediaType.parse("image/jpg"), mFile2))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.34.197:8080/upload")
                .post(multipartBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });

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