package com.lingtao.ltvideo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lingtao.ltvideo.R;

public class MatrixImageView extends View {

    private boolean isTop;
    private int imageRes;
    private Matrix matrix = new Matrix();
    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private int color;

    public MatrixImageView(Context context) {
        this(context, null);
    }

    public MatrixImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MatrixImageView);
        isTop = ta.getBoolean(R.styleable.MatrixImageView_matrix_top, false);
        imageRes = ta.getResourceId(R.styleable.MatrixImageView_matrix_bitmap, -1);
        color = ta.getColor(R.styleable.MatrixImageView_matrix_color, Color.WHITE);
        ta.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(6);
        paint.setAlpha(170);


        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.FILL);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.BLUE);
        paint2.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (false) {
            canvas.drawColor(color);
            matrix.postScale(2, 2);
//        canvas.save();
//            canvas.setMatrix(matrix);
        canvas.concat(matrix);
            canvas.drawRect(100, 100, 300, 300, paint1);
//        canvas.restore();
            canvas.drawRect(300, 300, 500, 500, paint2);
            canvas.drawText("canvas.setMatrix(matrix)", 50, 600, paint1);
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageRes);
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int left = (width - bWidth) / 2;
        int top = (height - bHeight) / 2;
        if (isTop) {
//            matrix.setSkew(0.3f, 0.3f);
            matrix.postTranslate(left, top);
            matrix.postScale(2, 2,width/2,height/2);
            canvas.drawBitmap(bitmap, matrix, paint);

        } else {
            canvas.drawBitmap(bitmap, left, top, paint);
            canvas.drawLine(left, top, (left + bWidth), top, paint);
            canvas.drawLine((left + bWidth), top, (left + bWidth), (top + bHeight), paint);
            canvas.drawLine(left, (top + bHeight), (left + bWidth), (top + bHeight), paint);
            canvas.drawLine(left, top, left, (top + bHeight), paint);

        }

    }
}
