package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.widgets.StickyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickyScrollViewActivity extends AppCompatActivity {

    @BindView(R.id.scrollView_layout)
    StickyScrollView scrollViewLayout;

    public static void start(Context context) {
        Intent starter = new Intent(context, StickyScrollViewActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_scroll_view);
        ButterKnife.bind(this);


    }



}