package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.bean.ColorMatrixBean;
import com.lingtao.ltvideo.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ImageViewDimActivity extends AppCompatActivity {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.seekbar)
    SeekBar seekbar;

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageViewDimActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_dim);
        ButterKnife.bind(this);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.d("onProgressChanged: " + progress);

                int s = progress / 10;
                int i = progress % 10;
                LogUtils.d("onProgressChanged: " + i);

                float see = progress / ((float) 125);
                float v = 1 - see;
                if (s == 0) {
//                        Glide.with(ImageViewDimActivity.this).load(R.drawable.erciyuan)
//                                .into(imageView);
                    Bitmap originBmp = BitmapFactory.decodeResource(getResources(), R.drawable.erciyuan);
                    imageView.setImageBitmap(originBmp);
                } else {


                    ColorMatrix matrix = new ColorMatrix(new float[]{
                            1, 0, 0, 0, 0,
                            0, 1, 0, 0, 0,
                            0, 0, 1, 0, 0,
                            0, 0, 0, v, 0,
                    });
                    Bitmap bitmap = handleColorRotateBmp(matrix, R.drawable.erciyuan);
                        Glide.with(ImageViewDimActivity.this)
                                .asBitmap()
                                .addListener(new RequestListener<Bitmap>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).load(R.drawable.erciyuan)
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, s)))
                                .into(imageView);
                        Glide.with(ImageViewDimActivity.this).load(bitmap).into(imageView);
                    imageView.setImageBitmap(bitmap);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Bitmap handleColorRotateBmp(ColorMatrix colorMatrix, int res) {

        Bitmap originBmp = BitmapFactory.decodeResource(getResources(), res);
        if (originBmp == null) {
            return null;
        }
        Bitmap tempBmp = Bitmap.createBitmap(originBmp.getWidth(), originBmp.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(tempBmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// 设置颜色变换效果

        canvas.drawBitmap(originBmp, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return tempBmp;
    }

}