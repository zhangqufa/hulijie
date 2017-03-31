/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.text.TextUtils;
import android.util.Log;

public class AppLog {
    private static final String sTAG = AppLog.class.getSimpleName();
    private static boolean DEBUG = Constant.LOG_DEBUG;// 是否开启日志 ，默认开启true

    private AppLog() {
    }

    public static void showLog(boolean show) {
        DEBUG = show;
    }

    enum LogType {
        DEBUG, ERROR, INFO, WARN
    }

    public static void Log(String msg) {
        Log("", msg);
    }

    private static void Log(String TAG, String msg) {
        if (!DEBUG) {
            return;
        }
        if (TextUtils.isEmpty(TAG)) {
            TAG = sTAG;
        }
        Log.i(TAG, msg);
    }

    public static void Log(String TAG, String msg, boolean bModuleShow) {
        if (!DEBUG || !bModuleShow) {
            return;
        }
        if (TextUtils.isEmpty(TAG)) {
            TAG = sTAG;
        }
        Log.i(TAG, msg);
    }

    public static void Log(LogType logType, String msg) {
        Log(logType, "", msg, true);
    }

    public static void Log(LogType logType, String msg, boolean bModuleShow) {
        Log(logType, "", msg, bModuleShow);
    }

    private static void Log(LogType logType, String TAG, String msg, boolean bModuleShow) {
        if (!DEBUG || !bModuleShow) {
            return;
        }
        if (TextUtils.isEmpty(TAG)) {
            TAG = sTAG;
        }
        if (LogType.DEBUG.compareTo(logType) == 0) {
            Log.d(TAG, msg);
        } else if (LogType.ERROR.compareTo(logType) == 0) {
            Log.e(TAG, msg);
        } else if (LogType.INFO.compareTo(logType) == 0) {
            Log.i(TAG, msg);
        } else if (LogType.WARN.compareTo(logType) == 0) {
            Log.w(TAG, msg);
        } else {
            Log.i(TAG, msg);
        }
    }

}
