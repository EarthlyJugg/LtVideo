package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.adapter.ColorMatrixAdapter;
import com.lingtao.ltvideo.bean.ColorMatrixBean;
import com.lingtao.ltvideo.util.LogUtils;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片处理
 */
public class PictureProcessingActivity extends AppCompatActivity implements View.OnTouchListener {


    private static final int REQUEST_PICKER_AND_CROP = 9999;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.listView)
    ListView listView;
    //    private String picturePath = "/storage/emulated/0/ajb_home/share/logo/logo_asset.png";
//    private String picturePath = "/storage/emulated/0/timg.jpg";
    private String picturePath = "/storage/emulated/0/erciyuan.jpg";

    public static void start(Context context) {
        Intent starter = new Intent(context, PictureProcessingActivity.class);
        context.startActivity(starter);
    }

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_processing);
        ButterKnife.bind(this);
        initListView();
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        imageView.setImageBitmap(bm);

//        mGestureDetector = new GestureDetector(new gestureListener()); //使用派生自OnGestureListener
        mGestureDetector = new GestureDetector(new simpleGestureListener());
        imageView.setOnTouchListener(this);
        imageView.setFocusable(true);
        imageView.setClickable(true);
        imageView.setLongClickable(true);
    }

    private void initListView() {
        ColorMatrixAdapter adapter = new ColorMatrixAdapter(this, getData());
        listView.setAdapter(adapter);
        adapter.setOnItemClick(new ColorMatrixAdapter.onItemClick() {
            @Override
            public void onClick(ColorMatrixBean bean, int position) {
                imageView.setImageBitmap(handleColorRotateBmp(bean.getColorMatrix()));
            }
        });
    }

    private List<ColorMatrixBean> getData() {
        List<ColorMatrixBean> list = new LinkedList<>();
        list.add(new ColorMatrixBean("原图", new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        })));
        list.add(new ColorMatrixBean("灰度", new ColorMatrix(new float[]{0.33F, 0.59F, 0.11F, 0F, 0F, 0.33F, 0.59F, 0.11F, 0F, 0F, 0.33F, 0.59F, 0.11F, 0F, 0F, 0F, 0F, 0F, 1F, 0F})));
        list.add(new ColorMatrixBean("黑白", new ColorMatrix(new float[]{
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0, 0, 0, 1, 0,
        })));
        list.add(new ColorMatrixBean("高亮", new ColorMatrix(new float[]{
                1.2f, 0, 0, 0, 0,
                0, 1.2f, 0, 0, 0,
                0, 0, 1.2f, 0, 0,
                0, 0, 0, 1, 0,
        })));
        list.add(new ColorMatrixBean("怀旧", new ColorMatrix(new float[]{
                0.394F, 0.769F, 0.189F, 0F, 0F,
                0.349F, 0.6856F, 0.168F, 0F, 0F,
                0.272F, 0.534F, 0.131F, 0F, 0F,
                0F, 0F, 0F, 1F, 0F
        })));
        list.add(new ColorMatrixBean("高饱和度", new ColorMatrix(new float[]{
                1.438F, -0.122F, -0.016F, 0F, -0.03F,
                -0.062F, 1.378F, -0.016F, 0F, 0.05F,
                -0.062F, -0.122F, 1.483F, 0F, -0.02F,
                0F, 0F, 0F, 1F, 0F
        })));
        list.add(new ColorMatrixBean("颜色反向", new ColorMatrix(new float[]{
                -1, 0, 0, 0, 255,
                0, -1, 0, 0, 255,
                0, 0, -1, 0, 255,
                0, 0, 0, 1, 0
        })));
        return list;
    }


    public void choosePicture(View view) {

        /**
         * create()方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断
         * selectPicture()方法参数分别为 是否裁剪、裁剪后图片的宽(单位px)、裁剪后图片的高、宽比例、高比例。都不传则默认为裁剪，宽200，高200，宽高比例为1：1。
         */
        PictureSelector
                .create(this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(false, 200, 200, 1, 1);




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                LogUtils.d("onActivityResult: " + picturePath);
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bm);


            }
        } else if (requestCode == REQUEST_PICKER_AND_CROP) {
            Uri uri = data.getData();
//            LogUtils.d("onActivityResult: " + uri.getPath());
        }
    }


    /**
     * tempBmp   需要处理生成的bitmap
     * originBmp 原始bitmap
     */
    private Bitmap handleColorRotateBmp(ColorMatrix colorMatrix) {

        Bitmap originBmp = BitmapFactory.decodeFile(picturePath);
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


    public void caijian(View view) {
        if (true) {
            PictureCropActivity.start(this);
            return;
        }
        Uri uri = Uri.fromFile(new File(picturePath));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //intent.putExtra("crop", "true");//发送裁剪信号
        intent.putExtra("aspectX", 1);//X 方向的比例
        intent.putExtra("aspectY", 1);//同上
        intent.putExtra("outputX", 300);//裁剪区的宽
        intent.putExtra("outputY", 300);//
        intent.putExtra("scale", true);//是否保留比例
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
        intent.putExtra("return-data", false);//是否将图片数据保留在Bitmap中返回
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//文件格式
        intent.putExtra("noFaceDetection", true); // no face detection
        intent = Intent.createChooser(intent, "裁剪图片");//
        startActivityForResult(intent, REQUEST_PICKER_AND_CROP);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener {

        /**
         * @param e1
         * @param e2
         * @param distanceX 相对于上次移动的X 方向的距离  正数为左滑动 负数为右滑
         * @param distanceY 相对于上次移动的Y 方向的距离 正数为上滑动 负数为下滑
         * @return
         */
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            LogUtils.d("simpleGestureListener_log", "onScroll: x" + distanceX + ",y:" + distanceY);
            LogUtils.d("simpleGestureListener_log", "e1:x->" + e1.getX() + ",e1:y->" + e1.getY()+"--------e2:x->" + e2.getX() + ",e2:y->" + e2  .getY());

            return true;
        }

        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;

        // 触发条件 ：
        // X轴的坐标位移大于 FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
        // 参数解释：
        // e1：第1个 ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            LogUtils.d("simpleGestureListener_log", "x:" + velocityX + ",y:" + velocityY);
            //当滑动的距离大于指定的距离时  ，当滑动的速度大于指定的距离时
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                LogUtils.d("MyGesture", "左滑");
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                LogUtils.d("MyGesture", "右滑");
            } else if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                LogUtils.d("MyGesture", "上滑");
            } else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                LogUtils.d("MyGesture", "下滑");
            }
            return true;
        }


    }

}