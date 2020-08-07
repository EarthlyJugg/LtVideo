package com.lingtao.ltvideo.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.lingtao.ltvideo.R;

public class VideoPlayerBtn extends View {

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    private static final String TAG = "VideoPlayerBtn";


    private int widthSize;
    private int heightSize;

    private Paint mPaint;
    private Paint mPaint2;
    private Path mPath;

    private boolean isDown = false;//是否是按下状态
    private RectF rectF;
    private ValueAnimator animator;
    private ValueAnimator animator2;
    private int startAngle;
    private int sweepAngle;


    public VideoPlayerBtn(Context context) {
        this(context, null);
    }

    public VideoPlayerBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayerBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.GREEN);
        mPaint2.setStrokeWidth(5);
        mPaint2.setStyle(Paint.Style.STROKE);

        mPath = new Path();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.EXACTLY://精确的数值和match_parent
                break;
            case MeasureSpec.AT_MOST://wrap_content
            case MeasureSpec.UNSPECIFIED://不限制
                widthSize = DEFAULT_WIDTH;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY://精确的数值和match_parent
                break;
            case MeasureSpec.AT_MOST://wrap_content
            case MeasureSpec.UNSPECIFIED://不限制
                heightSize = DEFAULT_HEIGHT;
                break;
        }

        rectF = new RectF();
        rectF.left = 5;
        rectF.top = 5;
        rectF.right = widthSize - 5;
        rectF.bottom = heightSize - 5;
//        mPath.addOval(rectF, Path.Direction.CW);


        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
//                postInvalidate();
                startDrwnCircle();
                startDrwnCircle2();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                animator.cancel();
                animator2.cancel();
                animator.removeAllUpdateListeners();
                animator2.removeAllUpdateListeners();
                postInvalidate();
                break;
        }
        return true;
    }

    private void startDrwnCircle2() {
        if (animator2 != null) {
            animator2.cancel();
            animator2.removeAllUpdateListeners();
        }
        animator2 = ValueAnimator.ofInt(-90, 360);
        animator2.setDuration(5 * 1000);
//        animator2.setRepeatCount(10000);
//        animator2.setRepeatMode(ValueAnimator.RESTART);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                startAngle = curValue;
                Log.d(TAG, "onAnimationUpdate: " + curValue);
            }
        });
        animator2.start();
    }

    private void startDrwnCircle() {
        if (animator != null) {
            animator.cancel();
            animator.removeAllUpdateListeners();
        }
        animator = ValueAnimator.ofInt(0, 360);
        animator.setDuration(5 * 1000);
//        animator.setRepeatCount(10000);
//        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                sweepAngle = curValue;
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(widthSize / 2, heightSize / 2, heightSize / 2 - 20, mPaint);
        if (isDown) {
            canvas.drawArc(rectF, -90, sweepAngle, false, mPaint2);
        }


    }


}
