package com.ssj.hulijie.pro.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.home.view.MainActivity;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SplashMainActivity extends BaseActivity {

    private TextView tv_flash;
    private ImageView iv_flash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashMainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }
}
