package com.ssj.hulijie.base;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.ssj.hulijie.pro.db.helper.TemplateConfig;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by vic_zhang .
 * on 2017/3/25
 */

public class HljAppliation extends Application {

    public static Context context;
    public static TemplateConfig config;
    public static String currentCity="台州";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //init nohttp
        NoHttp.initialize(this);
        Logger.setDebug(true);


        //ptr refresh
        PtrFrameLayout.DEBUG = true;


        /**
         * init database
         */
        initDatabase();

        //init baidu map
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        initBaiduMap();

        /**
         * init 右滑返回
         */
        BGASwipeBackManager.getInstance().init(this);

    }


    private void initBaiduMap() {
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initDatabase() {
        //加载配置文件
        config = new TemplateConfig();
        try {
            String[] files = getAssets().list("");
            for (String file : files) {
                if (file.endsWith(".orm.xml")) {
                    config.mappings.put(file, config.parse(getAssets().open(file)));
                }
            }
        } catch (Exception e) {
            AppLog.Log("数据库读取orm文件失败");
            e.printStackTrace();
        }
    }


    public static Context getContext() {
        return context;
    }

}
