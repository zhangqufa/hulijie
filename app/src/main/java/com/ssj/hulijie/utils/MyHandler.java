package com.ssj.hulijie.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author vic_zhang .
 *         on 2017/12/12
 */

public class MyHandler extends Handler {
    private WeakReference<? extends Activity> activityWeakReference;

    public MyHandler(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);

    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        Activity activity = activityWeakReference.get();
        if (activity != null && !activity.isFinishing()) {
            if (handlerCallback != null) {
                handlerCallback.handleMessage(msg);
            }
        }

    }

    private HandlerCallback handlerCallback;

    public void setHandlerCallback(HandlerCallback handlerCallback) {
        this.handlerCallback = handlerCallback;
    }

    public interface HandlerCallback {
        /**
         * handler 回调
         *
         * @param msg
         */
        void handleMessage(Message msg);
    }
}
