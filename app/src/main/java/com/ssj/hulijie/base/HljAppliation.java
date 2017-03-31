package com.ssj.hulijie.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.NoHttp;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by vic_zhang .
 * on 2017/3/25
 */

public class HljAppliation extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //init nohttp
        NoHttp.initialize(this);

        //ptr refresh
        PtrFrameLayout.DEBUG = true;

        //init fresco
        Fresco.initialize(this);

    }


    public static Context getContext() {
        return context;
    }
}
