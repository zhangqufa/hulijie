package com.ssj.hulijie.utils;

import android.text.TextUtils;

import static android.R.attr.format;

/**
 * Created by Administrator on 2017/7/27.
 */

public class StringFormat {

    public static String toMobile(String mobile) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(mobile) && mobile.length() == 11) {

            String mobile_prefix = mobile.substring(0, 3);
            sb.append(mobile_prefix);
            sb.append("****");
            String mobile_postfix = mobile.substring(7, mobile.length());
            sb.append(mobile_postfix);
            return sb.toString();
        }
        return mobile;
    }

}
