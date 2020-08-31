package com.lingtao.ltvideo.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.helper.GifHandler;

import java.io.File;

public class PlayGIFActivity extends AppCompatActivity {

    private static final String TAG = "PlayGIFActivity_log";

    static {
        System.loadLibrary("native-lib");
    }

    Bitmap bitmap;
    GifHandler gifHandler;
    ImageView imageView;

    public static void start(Context context) {
        Intent starter = new Intent(context, PlayGIFActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_g_i_f);
        imageView = (ImageView) findViewById(R.id.image);
    }


    public void playGif(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "demo2.gif");
        gifHandler = new GifHandler(file.getAbsolutePath());
        Log.i("tuch", "ndkLoadGif: " + file.getAbsolutePath());
        //得到gif   width  height  生成Bitmap
        int width = gifHandler.getWidth();
        int height = gifHandler.getHeight();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int nextFrame = gifHandler.updateFrame(bitmap);
        handler.sendEmptyMessageDelayed(1, nextFrame);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mNextFrame = gifHandler.updateFrame(bitmap);
            handler.sendEmptyMessageDelayed(1, mNextFrame);
            imageView.setImageBitmap(bitmap);
        }
    };

}