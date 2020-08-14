package com.lingtao.ltvideo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

public class OvalIndicatorView extends View implements ViewPager.OnPageChangeListener {
    // private int mSelectColor = Color.parseColor("#E38A7C");
    private int mSelectColor = Color.parseColor("#FFFFFF");
    private Paint mCirclePaint;
    private int mCount = 5; // indicator 的数量
    private int mRadius;//半径
    private int mDotNormalColor;// 小圆点默认颜色
    private int mSpace = 0;// 圆点之间的间距
    private List<Indicator> mIndicators;
    private int mSelectPosition = 0; // 选中的位置
    private ViewPager mViewPager;
    private OnIndicatorClickListener mOnIndicatorClickListener;
    /**
     * 是否允许点击Indicator切换ViewPager
     */
    private boolean mIsEnableClickSwitch = false;
    private Path mPath;

    public OvalIndicatorView(Context context) {
        super(context);
        init();
    }

    public OvalIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(context, attrs);
        init();
    }

    public OvalIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OvalIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context, attrs);
        init();
    }

    private void init() {

        mCirclePaint = new Paint();
        mCirclePaint.setDither(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        // 默认值
        mIndicators = new ArrayList<>();
        mPath = new Path();
        initValue();

    }

    private void initValue() {
        mCirclePaint.setColor(mDotNormalColor);

        invalidate();
    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     */
    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OvalIndicatorView);
        mRadius = (int) typedArray.getDimensionPixelSize(R.styleable.OvalIndicatorView_indicatorRadius, DisplayUtils.dpToPx(6));
        // color
        mSelectColor = typedArray.getColor(R.styleable.OvalIndicatorView_indicatorSelectColor, Color.WHITE);
        mDotNormalColor = typedArray.getColor(R.styleable.OvalIndicatorView_indicatorColor, Color.GRAY);

        mIsEnableClickSwitch = typedArray.getBoolean(R.styleable.OvalIndicatorView_enableIndicatorSwitch, false);

        typedArray.recycle();
    }

    /**
     * 测量每个圆点的位置
     */
    private void measureIndicator() {
        mIndicators.clear();
        float cx = 0;
        for (int i = 0; i < mCount; i++) {
            Indicator indicator = new Indicator();
            if (i == 0) {
                cx = mRadius * 2;
            } else {
                cx += mRadius * 4 + mSpace;
            }

            indicator.cx = cx;
            indicator.cy = getMeasuredHeight() / 2;

            mIndicators.add(indicator);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mRadius * 4 * mCount + mSpace * (mCount - 1);
        int height = (mRadius + mSpace) * 2;
        setMeasuredDimension(width, height);

        measureIndicator();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < mIndicators.size(); i++) {

            Indicator indicator = mIndicators.get(i);
            float x = indicator.cx;
            float y = indicator.cy;

            if (mSelectPosition == i) {
                mCirclePaint.setColor(mSelectColor);
                RectF rectF = new RectF();
                rectF.left = x - 2 * mRadius;
                rectF.top = y - mRadius;
                rectF.right = x + 2 * mRadius;
                rectF.bottom = y + mRadius;
                canvas.drawRoundRect(rectF, mRadius, mRadius, mCirclePaint);
            } else {
                mCirclePaint.setColor(mDotNormalColor);
                canvas.drawCircle(x, y, mRadius, mCirclePaint);

            }

        }

    }

    private void setPath(Indicator indicator) {
        float x = indicator.cx;
        float y = indicator.cy;
//        int lineWithR = mRadius + (mStrokeWidth / 2);

        mPath.reset();
        mPath.moveTo(x - mRadius, y - mRadius);
        mPath.lineTo(x + mRadius, y - mRadius);
        mPath.quadTo(x + 2 * mRadius, y, x + mRadius, y + mRadius);
        mPath.lineTo(x - mRadius, y + mRadius);
        mPath.quadTo(x - 2 * mRadius, y, x - mRadius, y - mRadius);
        mPath.close();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPoint = 0;
        float yPoint = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xPoint = event.getX();
                yPoint = event.getY();
                handleActionDown(xPoint, yPoint);
                break;

        }

        return super.onTouchEvent(event);
    }

    private void handleActionDown(float xDis, float yDis) {
        for (int i = 0; i < mIndicators.size(); i++) {
            Indicator indicator = mIndicators.get(i);
            if (xDis < (indicator.cx + mRadius)
                    && xDis >= (indicator.cx - (mRadius))
                    && yDis >= (yDis - (indicator.cy))
                    && yDis < (indicator.cy + mRadius)) {
                // 找到了点击的Indicator
                // 是否允许切换ViewPager
                if (mIsEnableClickSwitch) {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(i, false);
                    }
                    mSelectPosition = i;
                    invalidate();
                }

                // 回调
                if (mOnIndicatorClickListener != null) {
                    mOnIndicatorClickListener.onSelected(i);
                }
                break;
            }
        }
    }

    public void setOnIndicatorClickListener(OnIndicatorClickListener onIndicatorClickListener) {
        mOnIndicatorClickListener = onIndicatorClickListener;
    }

    private void setCount(int count) {
        mCount = count;
        invalidate();
    }


    /**
     * 设置选中指示器的颜色
     *
     * @param selectColor
     */
    public void setSelectColor(int selectColor) {
        mSelectColor = selectColor;
        initValue();
    }

    /**
     * 设置指示器默认颜色
     *
     * @param dotNormalColor
     */
    public void setDotNormalColor(int dotNormalColor) {
        mDotNormalColor = dotNormalColor;
        initValue();
    }

    /**
     * 设置选中的位置
     *
     * @param selectPosition
     */
    public void setSelectPosition(int selectPosition) {
        mSelectPosition = selectPosition;
        invalidate();
    }

    /**
     * 设置Indicator 半径
     *
     * @param radius
     */
    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    public void setSpace(int space) {
        mSpace = space;
        invalidate();
    }

    public void setEnableClickSwitch(boolean enableClickSwitch) {
        mIsEnableClickSwitch = enableClickSwitch;
    }

    /**
     * 与ViewPager 关联
     *
     * @param viewPager
     */
    public void setUpWithViewPager(ViewPager viewPager) {
        releaseViewPager();
        if (viewPager == null) {
            return;
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        int count = mViewPager.getAdapter().getCount();
        setCount(count);
    }

    /**
     * 重置ViewPager
     */
    private void releaseViewPager() {
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
            mViewPager = null;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Indicator 点击回调
     */
    public interface OnIndicatorClickListener {
        void onSelected(int position);
    }


    private static class Indicator {
        public float cx; // 圆心x坐标
        public float cy; // 圆心y 坐标
    }


}
