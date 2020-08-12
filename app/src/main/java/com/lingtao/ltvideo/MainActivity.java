package com.lingtao.ltvideo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.activity.CameraPreviewActivity;
import com.lingtao.ltvideo.activity.PictureCropActivity;
import com.lingtao.ltvideo.activity.PictureProcessingActivity;
import com.lingtao.ltvideo.activity.StickyScrollViewActivity;
import com.lingtao.ltvideo.activity.StickyScrollViewActivity2;
import com.lingtao.ltvideo.activity.StickyScrollViewActivity3;
import com.lingtao.ltvideo.bean.PictureBean;
import com.lingtao.ltvideo.tietu.PageActivity;
import com.lingtao.ltvideo.tietu.PictureHodlerMainActivity;
import com.lingtao.ltvideo.util.LogUtils;
import com.wildma.pictureselector.PictureSelector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toCameraPreview)
    Button toCameraPreview;

//    static {
//        System.loadLibrary("native-lib");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public native String stringFromJNI();

    @OnClick({R.id.toCameraPreview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toCameraPreview:
                startActivity(new Intent(this, CameraPreviewActivity.class));
                break;
        }
    }

    public void openPhone(View view) {


        PictureProcessingActivity.start(this);

    }

    public void openPictureCrop(View view) {
        PictureCropActivity.start(this);
    }

    public void openPictureHodler(View view) {
        PictureHodlerMainActivity.start(this);
    }

    public void toPageActivity(View view) {
        PageActivity.start(this);
    }

    public void toStickyScrollViewActivity(View view) {
        StickyScrollViewActivity.start(this);
    }

    public void toStickyScrollViewActivity2(View view) {
        StickyScrollViewActivity2.start(this);

    }

    public void toStickyScrollViewActivity3(View view) {
        StickyScrollViewActivity3.start(this);
    }
}
