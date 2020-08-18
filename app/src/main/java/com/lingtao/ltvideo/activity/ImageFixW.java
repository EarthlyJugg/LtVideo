package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.adapter.GirlAdapter;
import com.lingtao.ltvideo.bean.GirlItemData;
import com.lingtao.ltvideo.service.ImageService;
import com.lingtao.ltvideo.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageFixW extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading)
    Button loading;
    private String TAG = "ImageFixW_log";
    private String mSubtype = "4";
    List<GirlItemData> list = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageFixW.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fix_w);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

//        Glide.with(this)
//                .load(R.drawable.zhengfangxing)
//                .into(imageView);

        for (int i = 0; i < 5; i++) {
            list.add(new GirlItemData(R.drawable.erciyuan));
            list.add(new GirlItemData(R.drawable.zhengfangxing));
            list.add(new GirlItemData(R.drawable.shamo));
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pictureDownloadFinish(List<GirlItemData> datas) {
        Toast.makeText(this, "图片加载完成", Toast.LENGTH_SHORT).show();
        for (GirlItemData data : datas) {
            LogUtils.d("pictureDownloadFinish: w=" + data.getWidth() + ",h=" + data.getHeight());
        }
        recyclerView.setAdapter(new GirlAdapter(datas, this));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.loading)
    public void onViewClicked() {

        ImageService.startService(this, list, mSubtype);

    }
}