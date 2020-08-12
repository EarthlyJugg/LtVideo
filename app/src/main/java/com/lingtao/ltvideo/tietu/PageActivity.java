package com.lingtao.ltvideo.tietu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lingtao.ltvideo.R;


public class PageActivity extends Activity {

    public static void start(Context context) {
        Intent starter = new Intent(context, PageActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
    }

}