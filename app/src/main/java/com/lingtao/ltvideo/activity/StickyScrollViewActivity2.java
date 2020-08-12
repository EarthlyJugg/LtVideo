package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.widgets.StickyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickyScrollViewActivity2 extends AppCompatActivity {

    @BindView(R.id.scrollView_layout)
    StickyScrollView nestedScrollView;
    @BindView(R.id.top1Text)
    TextView top1Text;
    @BindView(R.id.top2Text)
    TextView top2Text;
    @BindView(R.id.top3Text)
    TextView top3Text;

    private int nestedScrollViewTop;

    public static void start(Context context) {
        Intent starter = new Intent(context, StickyScrollViewActivity2.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_scroll_view2);
        ButterKnife.bind(this);
    }

    public void scrollByDistance(int dy) {
        if (nestedScrollViewTop == 0) {
            int[] intArray = new int[2];
            nestedScrollView.getLocationOnScreen(intArray);
            nestedScrollViewTop = intArray[1];
        }
        int distance = dy - nestedScrollViewTop;//必须算上nestedScrollView本身与屏幕的距离
        nestedScrollView.fling(distance);//添加上这句滑动才有效
        nestedScrollView.smoothScrollBy(0, distance);
    }

    public void top1(View view) {

        nestedScrollView.scrollTo(0,top1Text.getTop());
    }

    public void top2(View view) {
        nestedScrollView.scrollTo(0,top2Text.getTop());
    }

    public void top3(View view) {
        nestedScrollView.scrollTo(0,top3Text.getTop());
    }
}