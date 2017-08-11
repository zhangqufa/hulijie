package com.ssj.hulijie.pro.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SplashFirstActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SplashMainActivity.class));
        finish();
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }
}
