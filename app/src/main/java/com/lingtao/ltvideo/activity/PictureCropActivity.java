package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.util.LogUtils;
import com.lingtao.ltvideo.widgets.BigView;
import com.lingtao.ltvideo.widgets.SeniorCropImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片裁剪解码
 */
public class PictureCropActivity extends AppCompatActivity {

    //    @BindView(R.id.bigView)
    BigView bigView;
    SeniorCropImageView mSeniorImageView;
    @BindView(R.id.imageView)
    ImageView imageView;
    private String picturePath = "/storage/emulated/0/erciyuan.jpg";

    public static void start(Context context) {
        Intent starter = new Intent(context, PictureCropActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_crop);
        ButterKnife.bind(this);

//


    }

    private void loadBig() {
        InputStream is = null;
        try {
            is = getAssets().open("changtu.jpg");
            bigView.setImage(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将图片放大至屏幕，然后裁剪制定高度的图片
    public static Bitmap DrawSmailImage(Context context, int resourceID, int drawHeight) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);
        Bitmap drawBitmap = null;
        // 图片放大到屏幕大小
        if (imageBitmap != null) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = manager.getDefaultDisplay().getWidth();
            int screenHeight = manager.getDefaultDisplay().getHeight();

            int imageWidth = imageBitmap.getWidth();
            int imageHeight = imageBitmap.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) imageWidth) / screenWidth;
            float scaleHeight = ((float) imageHeight) / screenHeight;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            drawBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, screenWidth, drawHeight, matrix, true);
        }
        return drawBitmap;
    }

    // 将图片放大至屏幕
    public static Bitmap DrawScreenImage(Context context, int resourceID) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);
        Bitmap drawBitmap = null;
        // 图片放大到屏幕大小
        if (imageBitmap != null) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = manager.getDefaultDisplay().getWidth();
            int screenHeight = manager.getDefaultDisplay().getHeight();
            int imageWidth = imageBitmap.getWidth();
            int imageHeight = imageBitmap.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) imageWidth) / screenWidth;
            float scaleHeight = ((float) imageHeight) / screenHeight;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            drawBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, screenWidth, screenHeight, matrix, true);
        }
        return drawBitmap;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public void cropImage(View view) {
//        Bitmap imageViewBitmap = DrawScreenImage(this, R.drawable.erciyuan);
//        imageView.setImageBitmap(imageViewBitmap);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.erciyuan);
//        BitmapFactory.createBitmap(bitmap, x, y, width, height);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, 500, 500);
        imageView.setImageBitmap(bitmap);


    }
}