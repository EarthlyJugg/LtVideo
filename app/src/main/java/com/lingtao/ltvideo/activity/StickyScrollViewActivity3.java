package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.adapter.ScrollerViewAdapter;
import com.lingtao.ltvideo.bean.ScrollerBean;
import com.lingtao.ltvideo.widgets.StickyScrollView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickyScrollViewActivity3 extends AppCompatActivity {

    @BindView(R.id.scrollView_layout)
    StickyScrollView nestedScrollView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    public static void start(Context context) {
        Intent starter = new Intent(context, StickyScrollViewActivity3.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_scroll_view3);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ScrollerBean> list = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new ScrollerBean());
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new ScrollerViewAdapter(list));


    }


    public void top1(View view) {

    }

    public void top2(View view) {
    }

    public void top3(View view) {
    }
}