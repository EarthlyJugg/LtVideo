package com.lingtao.ltvideo.helper;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.lingtao.ltvideo.util.LogUtils;

public class gestureListener implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{


    @Override
    public boolean onDown(MotionEvent e) {
        /**
         * 用户按下屏幕就会触发
         */
        LogUtils.d("gesturelistener_log", "onDown: ");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        /**
         * 如果是按下的时间超过瞬间，而且在按下的时候没有松开或者是拖动的，那么onShowPress就会执行，具体这个瞬间是多久，我也不清楚呃……
         */
        LogUtils.d("gesturelistener_log", "onShowPress: ");
    }


    @Override
    public void onLongPress(MotionEvent e) {
        /**
         * 长按触摸屏，超过一定时长，就会触发这个事件
         */
        LogUtils.d("gesturelistener_log", "onLongPress: ");
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        /**
         * 从名子也可以看出,一次单独的轻击抬起操作,也就是轻击一下屏幕，
         * 立刻抬起来，才会有这个触发，当然,如果除了Down以外还有其它操作,
         * 那就不再算是Single操作了,所以也就不会触发这个事件
         *   触发顺序：
         *     点击一下非常快的（不滑动）Touchup：
         *     onDown->onSingleTapUp->onSingleTapConfirmed
         *     点击一下稍微慢点的（不滑动）Touchup：
         *     onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
         */
        LogUtils.d("gesturelistener_log", "onSingleTapUp: ");
        
        return false;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        /**
         * 滑屏，用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
         *  参数解释：
         *     e1：第1个ACTION_DOWN MotionEvent
         *     e2：最后一个ACTION_MOVE MotionEvent
         *     velocityX：X轴上的移动速度，像素/秒
         *     velocityY：Y轴上的移动速度，像素/秒
         */
        LogUtils.d("gesturelistener_log", "onFling: ");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        /**
         * 在屏幕上拖动事件。无论是用手拖动view，或者是以抛的动作滚动，都会多次触发,这个方法
         * 在ACTION_MOVE动作发生时就会触发
         *  滑屏：手指触动屏幕后，稍微滑动后立即松开
         *     onDown-----》onScroll----》onScroll----》onScroll----》………----->onFling
         *     拖动
         *     onDown------》onScroll----》onScroll------》onFiling
         *
         *     可见，无论是滑屏，还是拖动，影响的只是中间OnScroll触发的数量多少而已，最终都会触发onFling事件！
         */
        LogUtils.d("gesturelistener_log", "onScroll: ");
        return false;
    }

    /*--------GestureDetector.OnDoubleTapListener------------*/
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        /**
         * 单击事件。用来判定该次点击是SingleTap而不是DoubleTap，
         * 如果连续点击两次就是DoubleTap手势，如果只点击一次，
         * 系统等待一段时间后没有收到第二次点击则判定该次点击为SingleTap而不是DoubleTap，
         * 然后触发SingleTapConfirmed事件。触发顺序是：OnDown->OnsingleTapUp->OnsingleTapConfirmed
         * 关于onSingleTapConfirmed和onSingleTapUp的一点区别：
         * OnGestureListener有这样的一个方法onSingleTapUp，
         * 和onSingleTapConfirmed容易混淆。二者的区别是：onSingleTapUp，
         * 只要手抬起就会执行，而对于onSingleTapConfirmed来说，如果双击的话，
         * 则onSingleTapConfirmed不会执行。
         */
        LogUtils.d("gesturelistener_log", "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        /**
         * 双击事件
         */
        LogUtils.d("gesturelistener_log", "onDoubleTap: ");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        /**
         * 双击间隔中发生的动作。指触发onDoubleTap以后，在双击之间发生的其它动作，包含down、up和move事件；下图是双击一下的Log输出：
         */
        LogUtils.d("gesturelistener_log", "onDoubleTapEvent: ");
        return false;
    }
}

