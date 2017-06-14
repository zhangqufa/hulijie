package com.ssj.hulijie.pro.firstpage.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ListViewInScrollView extends ListView {

    public ListViewInScrollView(Context context) {
        super(context);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
