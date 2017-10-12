package com.ssj.hulijie.pro.firstpage.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by vic_zhang .
 * on 2017/8/11
 */

public class MyViewPaper extends ViewPager {
    private boolean isScroll = true;

    private ViewGroup mView;

    public void setParentView(ViewGroup view) {
        this.mView = view;
    }
    public MyViewPaper(Context context) {
        super(context);
    }

    public MyViewPaper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {


        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        if (!isScroll) {
            return false;
        }
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(e);
    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        if (isScroll)
//            return super.onInterceptTouchEvent(arg0);
//        else
//            return false;
//    }


//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        if (isScroll) {
//
//            switch (ev.getAction()) {
//                case MotionEvent.ACTION_MOVE:
//                    requestDisallowInterceptTouchEvent(true);
//                    break;
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                    requestDisallowInterceptTouchEvent(false);
//                    break;
//            }
//            return super.onTouchEvent(ev);
//        } else {
//            return false;
//        }
//    }

    public void setScollHoritial(boolean isScroll) {
        this.isScroll = isScroll;
    }
}
