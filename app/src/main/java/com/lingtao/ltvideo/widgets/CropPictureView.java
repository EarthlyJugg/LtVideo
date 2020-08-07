package com.lingtao.ltvideo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 裁剪控件
 */
public class CropPictureView extends FrameLayout implements View.OnTouchListener {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.cropRect)
    View cropRect;
    @BindView(R.id.parentLayout)
    FrameLayout parentLayout;
    /**
     * 横屏，为ture 这表示 裁剪的 宽 < 高  比例如 9：16    2:3     3:4     1：1
     * 竖屏，为false 这表示 裁剪的宽 > 高  比例如 16：9    3：2     4：3     1：1
     */
    private boolean screenOrientation = true;

    private GestureDetector mGestureDetector;


    public CropPictureView(Context context) {
        this(context, null);
    }

    public CropPictureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropPictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_crop_picture_view_layout, this);
        ButterKnife.bind(view);
        mGestureDetector = new GestureDetector(new simpleGestureListener());
        parentLayout.setOnTouchListener(this);
        parentLayout.setFocusable(true);
        parentLayout.setClickable(true);
        parentLayout.setLongClickable(true);
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
//            LogUtils.d("simpleGestureListener_log", "e1:x->" + e1.getX() + ",e1:y->" + e1.getY() +
//                    "--------e2:x->" + e2.getX() + ",e2:y->" + e2.getY());

//            LogUtils.d("simpleGestureListener_log", "onScroll: w=" + parentLayout.getWidth() + ",h=" + parentLayout.getHeight());

//            LogUtils.d("simpleGestureListener_log", "onScroll: w=" + imageView.getWidth() + ",h=" + imageView.getHeight());
            int height = parentLayout.getHeight();
            int cropRectHeight = cropRect.getHeight();
            int bottom = (height - cropRectHeight) / 2;

            float y1 = e1.getY();
            float y2 = e2.getY();
            int offset = (int) Math.abs(y1 - y2);
            if (distanceY > 0) {//上滑动
                LogUtils.d("simpleGestureListener_log", "onScroll: b:" + bottom + ",offset:" + offset);
                if (y2 > 0 && offset < bottom) {
                    imageView.layout(0, -offset, imageView.getWidth(), imageView.getHeight() - offset);
                }

            } else {//下滑
                if (y2 > height) {
                    return true;
                }
                imageView.layout(0, imageView.getHeight() + offset, imageView.getWidth(), -offset);
            }
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
