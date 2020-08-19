package com.lingtao.ltvideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.activity.CameraPreviewActivity;
import com.lingtao.ltvideo.activity.CardViewActivity;
import com.lingtao.ltvideo.activity.CircleIndicatorViewActivity;
import com.lingtao.ltvideo.activity.ImageFixW;
import com.lingtao.ltvideo.activity.PictureCropActivity;
import com.lingtao.ltvideo.activity.PictureProcessingActivity;
import com.lingtao.ltvideo.activity.StickyScrollViewActivity;
import com.lingtao.ltvideo.activity.StickyScrollViewActivity2;
import com.lingtao.ltvideo.activity.StickyScrollViewActivity3;
import com.lingtao.ltvideo.activity.SwipeCaptchaViewActivity;
import com.lingtao.ltvideo.activity.XfermodeViewActivity;
import com.lingtao.ltvideo.service.IWhileService;
import com.lingtao.ltvideo.tietu.PageActivity;
import com.lingtao.ltvideo.tietu.PictureHodlerMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toCameraPreview)
    Button toCameraPreview;
    @BindView(R.id.imageView)
    ImageView imageView;

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

    public void toCircleIndicaorViewActivity(View view) {
        CircleIndicatorViewActivity.start(this);
    }

    public void toSwipeCaptchaViewActivity(View view) {
        SwipeCaptchaViewActivity.start(this);
    }

    public void toXfermodeViewActivity(View view) {
        XfermodeViewActivity.start(this);
    }

    public void qihangdashabi(View view) {
        Bitmap mBmp = Bitmap.createBitmap(500 ,500 , Bitmap.Config.ARGB_8888);
        Canvas   mBmpCanvas = new Canvas(mBmp);
        Paint mPaint;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(100);
        mBmpCanvas.drawText("启舰大SB",0,100,mPaint);
        imageView.setImageBitmap(mBmp);
    }

    public void toImageFixW(View view) {
        ImageFixW.start(this);
    }

    public void toCardViewActivity(View view) {
        CardViewActivity.start(this);
    }

    public void toIWhileService(View view) {

        startService(new Intent(this, IWhileService.class));
    }
}
