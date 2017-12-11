package com.ssj.hulijie.versioncontrol;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/7.
 */

public class Version {

    /**
     * status : 1
     * content : 测试版本，全功能上线
     * url : 555555555
     * verName : 消防管理1.0.0.0
     * verCode : 1
     */

    private int status;  // 1: 强制更新
    private String content;
    private String url;
    private String verName;
    private int verCode;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("aaa", e.getMessage());
        }
        return verCode;
    }
}
