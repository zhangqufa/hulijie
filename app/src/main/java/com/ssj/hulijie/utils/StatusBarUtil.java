/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/1/17.
 */
public class StatusBarUtil {

    public static int getStatusBarHeight(Context context) {
        return getSystemDimen(context, "status_bar_height");
    }

    public static int getNavigationBarHeight(Context context) {
        return getSystemDimen(context, "navigation_bar_height");
    }

    private static int getSystemDimen(Context context, String str) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object instance = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField(str).get(instance).toString());
            // dip --> px
            statusHeight = context.getResources().getDimensionPixelSize(height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


}
