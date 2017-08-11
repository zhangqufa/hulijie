package com.ssj.hulijie.pro.firstpage.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by vic_zhang .
 * on 2017/8/11
 */

public class MyViewPaper extends ViewPager {
    private boolean isScroll = true;


    public MyViewPaper(Context context) {
        super(context);
    }

    public MyViewPaper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isScroll)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isScroll) {

            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    public void setScollHoritial(boolean isScroll) {
        this.isScroll = isScroll;
    }
}
