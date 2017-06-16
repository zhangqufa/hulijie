package com.ssj.hulijie.pro.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.home.view.MainActivity;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SplashMainActivity extends AppCompatActivity {

    private Handler mHandler ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_main);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashMainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }
}
