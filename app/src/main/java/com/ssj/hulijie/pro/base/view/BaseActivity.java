package com.ssj.hulijie.pro.base.view;


import android.os.Bundle;
import android.os.Message;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.mvp.view.impl.MvpActivity;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.nohttp.HttpResponseListener;
import com.ssj.hulijie.utils.DisplayUtils;
import com.ssj.hulijie.utils.MyHandler;
import com.ssj.hulijie.utils.StatusBarColorUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;


/**
 * BaseActivity---是我们项目的activity
 * Created by Dream on 16/5/26.
 */
public abstract class BaseActivity<P extends MvpBasePresenter> extends MvpActivity<P> implements SwipeBackActivityBase, MyHandler.HandlerCallback {
    //-------------- NoHttp -----------//


    /**
     * 用来标记取消。
     */
    private Object object = new Object();


    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    protected MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化请求队列，传入的参数是请求并发值。
        StatusBarColorUtils.setWindowStatusBarColor(this, R.color.comm_grey_666666);
        mQueue = NoHttp.newRequestQueue(1);
        mHandler.setHandlerCallback(this);
        DisplayUtils.initScreen(this);
    }


    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean
            isLoading) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }

    @Override
    protected void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(object);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();

        super.onDestroy();
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * 自定义封闭Handler
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
    }
}
