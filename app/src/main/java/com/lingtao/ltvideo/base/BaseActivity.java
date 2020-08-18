package com.lingtao.ltvideo.base;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initView(savedInstanceState);
    }

    protected abstract int layoutId();

    protected abstract void initView(Bundle savedInstanceState);

}