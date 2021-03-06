package com.lingtao.ltvideo.tietu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;


import androidx.annotation.Nullable;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.struct.DecalQueue;
import com.lingtao.ltvideo.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class DecalView extends BaseView {

    private final Paint mPaintForLineAndCircle;

    private int moveTag = 0;//当前触摸点是否在某一个贴纸的Bitmap范围内
    private int transformTag = 0;//
    private int deleteTag = 0;  //当前触摸点是否在某一个贴纸的删除按钮范围内


    private Bitmap deleteIcon;


    private List<ImageGroup> mDecalImageGroupList = new ArrayList<>();
//    private DecalQueue<ImageGroup> mDecalImageGroupList = new DecalQueue<>();


    public DecalView(Context context) {
        this(context, null);
    }

    public DecalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DecalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintForLineAndCircle = new Paint();
        mPaintForLineAndCircle.setAntiAlias(true);
        mPaintForLineAndCircle.setColor(Color.BLACK);
        mPaintForLineAndCircle.setAlpha(170);
        deleteIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_close);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ImageGroup imageGroup : mDecalImageGroupList) {
            float[] points = getBitmapPoints(imageGroup);
            float x1 = points[0];
            float y1 = points[1];//左上

            float x2 = points[2];
            float y2 = points[3];//右上

            float x3 = points[4];
            float y3 = points[5];//左下

            float x4 = points[6];
            float y4 = points[7];//右下

            canvas.drawLine(x1, y1, x2, y2, mPaintForLineAndCircle);
            canvas.drawLine(x2, y2, x4, y4, mPaintForLineAndCircle);
            canvas.drawLine(x4, y4, x3, y3, mPaintForLineAndCircle);
            canvas.drawLine(x3, y3, x1, y1, mPaintForLineAndCircle);

            canvas.drawCircle(x2, y2, 40, mPaintForLineAndCircle);
            canvas.drawBitmap(deleteIcon, x2 - deleteIcon.getWidth() / 2, y2 - deleteIcon.getHeight() / 2, mPaintForBitmap);

            canvas.drawBitmap(imageGroup.bitmap, imageGroup.matrix, mPaintForBitmap);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                anchorX = event.getX();//获取点击事件距离控件左边的距离，即视图坐标
                anchorY = event.getY();//获取点击事件距离控件顶边的距离，即视图坐标
                moveTag = decalCheck(anchorX, anchorY);
                deleteTag = deleteCheck(anchorX, anchorY);
                //当moveTag ！= -1 && deleteTag == -1（触摸点某一个贴纸范围内且不在任何删除按钮范围内）时
                if (moveTag != -1 && deleteTag == -1) {
                    downMatrix.set(mDecalImageGroupList.get(moveTag).matrix);
                    mode = DRAG;
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                moveTag = decalCheck(event.getX(0), event.getY(0));
                transformTag = decalCheck(event.getX(1), event.getY(1));
                /**
                 * 当moveTag != -1 && transformTag == moveTag && deleteTag == -1
                 * （两个触摸点在同一个贴纸范围内且不在任何删除按钮范围内）
                 */
                if (moveTag != -1 && transformTag == moveTag && deleteTag == -1) {
                    downMatrix.set(mDecalImageGroupList.get(moveTag).matrix);
                    mode = ZOOM;
                }
                /**
                 * 同时我们需要将当前两个触摸点之间的距离、中点、角度用oldDistance、midPoint、oldRotation保存起来以备使用。
                 */
                oldDistance = getDistance(event);
                oldRotation = getRotation(event);

                midPoint = midPoint(event);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM) {
                    moveMatrix.set(downMatrix);
                    float newRotation = getRotation(event) - oldRotation;
                    float newDistance = getDistance(event);
                    float scale = newDistance / oldDistance;
                    moveMatrix.postScale(scale, scale, midPoint.x, midPoint.y);// 縮放
                    moveMatrix.postRotate(newRotation, midPoint.x, midPoint.y);// 旋轉
                    if (moveTag != -1) {
                        mDecalImageGroupList.get(moveTag).matrix.set(moveMatrix);
                    }
                    invalidate();
                } else if (mode == DRAG) {
                    moveMatrix.set(downMatrix);
                    moveMatrix.postTranslate(event.getX() - anchorX, event.getY() - anchorY);// 平移
                    if (moveTag != -1) {
                        mDecalImageGroupList.get(moveTag).matrix.set(moveMatrix);
                    }
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (deleteTag != -1) {
                    mDecalImageGroupList.remove(deleteTag).release();
                    invalidate();
                }
                mode = NONE;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }
        return true;
    }

    private boolean pointCheck(ImageGroup imageGroup, float x, float y) {
        float[] points = getBitmapPoints(imageGroup);
        float x1 = points[0];
        float y1 = points[1];//左上

        float x2 = points[2];
        float y2 = points[3];//右上

        float x3 = points[4];
        float y3 = points[5];//左下

        float x4 = points[6];
        float y4 = points[7];//右下

        float edge = (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        if ((2 + Math.sqrt(2)) * edge >= Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2))
                + Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2))
                + Math.sqrt(Math.pow(x - x3, 2) + Math.pow(y - y3, 2))
                + Math.sqrt(Math.pow(x - x4, 2) + Math.pow(y - y4, 2))) {
            return true;
        }
        return false;
    }

    private boolean circleCheck(ImageGroup imageGroup, float x, float y) {
        float[] points = getBitmapPoints(imageGroup);
        float x2 = points[2];
        float y2 = points[3];

        int checkDis = (int) Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));

        if (checkDis < 40) {
            return true;
        }
        return false;
    }

    private int deleteCheck(float x, float y) {
        for (int i = 0; i < mDecalImageGroupList.size(); i++) {
            if (circleCheck(mDecalImageGroupList.get(i), x, y)) {
                return i;
            }
        }
        return -1;
    }

    private int decalCheck(float x, float y) {
        for (int i = 0; i < mDecalImageGroupList.size(); i++) {
            if (pointCheck(mDecalImageGroupList.get(i), x, y)) {
                return i;
            }
        }
        return -1;
    }

    public void addDecal(Bitmap bitmap) {
        ImageGroup imageGroupTemp = new ImageGroup();
        imageGroupTemp.bitmap = bitmap;
        LogUtils.d("DecalView_log", "addDecal: w" + getWidth() + ",h=" + getHeight());
        LogUtils.d("DecalView_log", "addDecal: w=" + imageGroupTemp.bitmap.getWidth() + ",h=" + imageGroupTemp.bitmap.getHeight());
        if (imageGroupTemp.matrix == null) {
            imageGroupTemp.matrix = new Matrix();
        }
        float transX = (getWidth() - imageGroupTemp.bitmap.getWidth()) / 2;
        float transY = (getHeight() - imageGroupTemp.bitmap.getHeight()) / 2;

        imageGroupTemp.matrix.postTranslate(transX, transY);
        imageGroupTemp.matrix.postScale(0.5f, 0.5f, getWidth() / 2, getHeight() / 2);
        mDecalImageGroupList.add(imageGroupTemp);
        postInvalidate();
    }
}