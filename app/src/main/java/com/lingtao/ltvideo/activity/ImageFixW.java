package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.lingtao.ltvideo.helper.ImageTask;
import com.lingtao.ltvideo.listener.OnLoadImageSizeListener;
import com.lingtao.ltvideo.service.INetWorkPicture;
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

public class ImageFixW extends AppCompatActivity implements OnLoadImageSizeListener<List<? extends INetWorkPicture>> {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading)
    Button loading;
    private String TAG = "ImageFixW_log";
    private String mSubtype = "4";
    List<GirlItemData> list = new ArrayList<>();
    private ImageTask task;

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

        for (int i = 0; i < 25; i++) {
//            list.add(new GirlItemData(R.drawable.erciyuan));
//            list.add(new GirlItemData(R.drawable.zhengfangxing));
//            list.add(new GirlItemData(R.drawable.shamo));

            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1950724334,1062157151&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1154649067,508133021&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2003743442,2569413743&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=328616222,1885605737&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2129329738,977954002&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3360820264,4291928003&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3459854359,4280605387&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1186687420,3065979840&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1002558273,2690237496&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1115399930,2493026341&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1735942010,1636519337&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3289985909,1312058910&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1460715933,4029459174&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2927359757,431647188&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1732264759,4009793510&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2172609652,1986930844&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1334598582,2311927740&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2648979847,3158248866&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1080916668,1617453055&fm=26&gp=0.jpg"));
            list.add(new GirlItemData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2617563084,2461921307&fm=26&gp=0.jpg"));
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if (task != null) {
            task.stop();
            task = null;
        }
        task = new ImageTask(this, this);
        task.execute(list);

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

    @OnClick({R.id.loading, R.id.cancel_button})
    public void onViewClicked(View view) {

//        ImageService.startService(this, list, mSubtype);
        switch (view.getId()) {
            case R.id.cancel_button:
                if (task != null && task.isRunning()) {
                    Log.d("ImageTask_log", "onViewClicked:取消");
                    task.stop();
                }
                break;
            case R.id.loading:
                if (task != null) {
                    task.stop();
                    task = null;
                }
                task = new ImageTask(this, this);
                task.execute(list);
                break;
        }


    }

    @Override
    public void onProgressUpdate(int progresse) {
        Log.d(TAG, "onProgressUpdate: " + progresse + "%");
    }

    @Override
    public void onLoadFinish(List<? extends INetWorkPicture> datas) {
        recyclerView.setAdapter(new GirlAdapter((List<GirlItemData>) datas, this));
    }
}