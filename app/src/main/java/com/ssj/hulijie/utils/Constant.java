package com.ssj.hulijie.utils;

import android.os.Environment;

/**
 * Created by vic_zhang .
 * on 2017/3/25
 */

public class Constant {

    //log打印开关,打包的时候 记得关闭，提高软件 性能
    public static final boolean LOG_DEBUG = true;
    public static final boolean Proxy = false;
    public static final int SUCCESS_CODE = 10000;
    public static final int ERROR_OVERDUE_CODE = 12007;
    public static final String APP_LOCAL_FILE_URL =Environment.getExternalStorageDirectory().getAbsoluteFile() + "/hulijie/image" ;

    public static final String tle_service = "13806583199";

}
