package com.lingtao.ltvideo.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;

    public static void show(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        mToast.show();
    }

}
