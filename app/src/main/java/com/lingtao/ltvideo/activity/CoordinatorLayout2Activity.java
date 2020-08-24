package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.lingtao.ltvideo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorLayout2Activity extends AppCompatActivity {


    @BindView(R.id.topLayout)
    RelativeLayout topLayout;
    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    public static void start(Context context) {
        Intent starter = new Intent(context, CoordinatorLayout2Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout2);
        ButterKnife.bind(this);
//        StatusBarUtil.setTransparent(this);

        StatusBarUtil.setTranslucentForImageView(this, 0, null);


        recyclerView = findViewById(R.id.recycle);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> strings = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            strings.add("我是条目" + x);
        }
        BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_coor_layout, strings) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_title, item);
            }
        };
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        baseQuickAdapter.isFirstOnly(false);
        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        recyclerView.setAdapter(baseQuickAdapter);

    }

    public void onExitMy(View view) {
        finish();
    }
}