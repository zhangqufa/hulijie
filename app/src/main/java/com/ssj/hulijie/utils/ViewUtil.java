package com.ssj.hulijie.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vic_zhang .
 * on 2017/7/29
 */

public class ViewUtil {

    private ViewUtil(){}

    /**
     * 改变控件件宽度
     * @param v
     * @param w
     */
    public static void zoomInViewSizeW(View v, int w) {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = w;
        v.setLayoutParams(lp);
    }

    /**
     * 改变控件高度
     * @param v
     * @param h
     */
    public static void zoomInViewSizeH(View v, int h) {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.height = h;
        v.setLayoutParams(lp);
    }
}
