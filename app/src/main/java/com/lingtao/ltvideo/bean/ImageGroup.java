package com.lingtao.ltvideo.bean;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageGroup {

    public Bitmap bitmap;
    public Matrix matrix = new Matrix();

    //删除贴纸时释放资源时使用
    public void release() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }

        if (matrix != null) {
            matrix.reset();
            matrix = null;
        }
    }

}
