package com.nahuo.library.widget.noscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;


/**
 * 不可滑动的GridView
 * Created by JorsonWong on 2015/7/22.
 */
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context) {
        super(context);

    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;// 禁止ListView进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }


}
