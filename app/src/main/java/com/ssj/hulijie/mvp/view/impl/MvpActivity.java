package com.ssj.hulijie.mvp.view.impl;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.mvp.view.MvpView;
import com.ssj.hulijie.utils.AppManager;


/**
 * 将我们的MVP架构集成到我们的Activity
 * MvpActivity---是MVP框架的
 * Created by vic on 16/5/26.
 */
public abstract class MvpActivity<P extends MvpBasePresenter> extends AppCompatActivity implements MvpView {

    //MVP架构P是动态的
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        presenter = bindPresenter();
        //我的presenter中介我是不是管理View---关联
        if (presenter != null) {
            presenter.attachView(this);
        }


    }


    //具体的实现我不知道,我给别人实现
    public abstract P bindPresenter();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁的时候---解除绑定
        if (presenter != null) {
            presenter.detachView();
        }
        AppManager.getAppManager().finishActivity(this);
    }
}
