package com.lingtao.ltvideo.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lingtao.ltvideo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity_log";
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.scale)
    Button scale;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouchEvent: 按下");
                        if (animator != null) {
                            animator.pause();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int rawX = (int) event.getRawX();
                        int rawY = (int) event.getRawY();
                        int width = imageView.getWidth() / 2;
                        int height = imageView.getHeight() / 2;

                        Log.d(TAG, "onTouch: x=" + rawX + ",y=" + rawY);

                        imageView.layout(rawX - width, rawY - height, rawX + width, rawY +height);
                        break;
                    case MotionEvent.ACTION_UP:
                        animator.resume();
                        Log.d(TAG, "onTouchEvent: 抬起");
                        break;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.start, R.id.scale, R.id.mufinish, R.id.imageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mufinish:
                finish();
                break;
            case R.id.imageView:
                Toast.makeText(this, "点击图片", Toast.LENGTH_SHORT).show();
                break;
            case R.id.start:
                break;
            case R.id.scale:
//                Animation animation = AnimationUtils.loadAnimation(this, R.anim.imageview_scale);
//                ScaleAnimation  scaleAnim = new ScaleAnimation(0.0f,1.4f,0.0f,1.4f,
//                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//                scaleAnim .setInterpolator(new BounceInterpolator());
//                scaleAnim.setDuration(2000);
//                imageView.startAnimation(scaleAnim);

//                RotateAnimation rotateAnim = new RotateAnimation(0, -650, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                rotateAnim.setDuration(3000);
//                rotateAnim .setInterpolator(new BounceInterpolator());
//                rotateAnim.setFillAfter(true);
//                imageView.startAnimation(rotateAnim);

                testAnimation();
                break;
        }
    }

    private void testAnimation() {

        animator = ValueAnimator.ofInt(0, 1200);
        animator.setDuration(2 * 1000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                imageView.layout(curValue - (imageView.getWidth()), 0, curValue, imageView.getHeight());
//                Log.d("qijian","curValue:"+curValue);
            }
        });
        animator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {

            }

            @Override
            public void onAnimationResume(Animator animation) {

            }
        });
        animator.start();
//        imageView.startAnimation(animation);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}