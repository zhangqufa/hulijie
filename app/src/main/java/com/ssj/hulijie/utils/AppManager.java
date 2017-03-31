/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.app.Activity;

import java.util.Stack;

//应用程序Activity管理类：用于Activity管理和应用程序退出
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    //单一实例
    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    // 添加Activity到堆栈
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    //获取当前Activity（堆栈中最后一个压入的）
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    //结束当前Activity（堆栈中最后一个压入的）
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    // 结束指定的Activity
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    // 结束指定类名的Activity
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    // 结束所有Activity
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    // 退出应用程序
    public void AppExit() {
        finishAllActivity();
        /**
         * System.exit(0);
         * 既然我们理解了，调用exit之后，程序会被杀死，整个程序都被意外（人为）的杀死了，
         * 这是非正常情况，故，Activiy的生命周期也不会继续执行了。
         * 很好的解释了为什么ondestroy不会执行的疑问。
         */
//        System.exit(0);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}