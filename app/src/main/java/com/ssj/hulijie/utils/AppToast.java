/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.content.Context;
import android.widget.Toast;

import com.ssj.hulijie.base.HljAppliation;


public class AppToast {

    private static Toast toast;


    private AppToast() {
    }

    public static void ShowToast(String text) {
        ShowToast(text, Toast.LENGTH_SHORT);
    }

    private static void ShowToast(String text, int duration) {
        ShowToast(HljAppliation.getContext(), text, duration);
    }

    private static void ShowToast(Context context, String text, int duration) {

        if (toast == null) {
            toast = Toast.makeText(context,
                    text,
                    duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
