package com.lingtao.ltvideo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.activity.CameraPreviewActivity;
import com.lingtao.ltvideo.activity.PictureProcessingActivity;
import com.lingtao.ltvideo.bean.PictureBean;
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

}
