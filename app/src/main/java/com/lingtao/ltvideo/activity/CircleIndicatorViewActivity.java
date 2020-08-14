package com.lingtao.ltvideo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lingtao.ltvideo.R;

public class CircleIndicatorViewActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, CircleIndicatorViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_indicator_view);
    }
}