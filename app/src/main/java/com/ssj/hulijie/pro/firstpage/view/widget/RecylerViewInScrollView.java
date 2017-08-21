package com.ssj.hulijie.pro.firstpage.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/7/11.
 */

public class RecylerViewInScrollView extends RecyclerView {

    public RecylerViewInScrollView(Context context) {
        super(context);
    }

    public RecylerViewInScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecylerViewInScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
