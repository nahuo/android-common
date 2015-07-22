package com.nahuo.library.widget.touchlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * touch监听layout，用于滑动返回到上一个界面
 * Created by JorsonWong on 2015/6/18.
 */
public class TouchLinearLayout extends LinearLayout {

    private final int barHeight;
    private OnTouchListener touchListener;
    private boolean shouldListener;
    private int side;
    private int downX, downY;
    private int canBackSide;

    public TouchLinearLayout(Context context) {
        this(context, null);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        side = dip2px(context, 10);
        canBackSide = dip2px(context, 20);
        barHeight = 0;
    }

    private int dip2px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                shouldListener = downX < side && downY > barHeight;
                break;
            case MotionEvent.ACTION_MOVE:
                if (shouldListener) {
                    int x = (int) (ev.getX() - downX);
                    if (x > canBackSide && Math.abs(x / (ev.getY() - downY)) > 1.1f) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchListener != null) {
            if (shouldListener) {
                touchListener.onTouch(this, event);
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                shouldListener = downX < side && downY > barHeight;
            }
        }
        return true;
    }

    public void setOnTouchListener(OnTouchListener l) {
        touchListener = l;
    }
}
