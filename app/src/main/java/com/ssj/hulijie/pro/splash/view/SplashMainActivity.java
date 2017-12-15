package com.ssj.hulijie.pro.splash.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
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
        tv_flash = (TextView) findViewById(R.id.tv_flash);
        iv_flash = (ImageView) findViewById(R.id.iv_flash);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator_scaleX = ObjectAnimator.ofFloat(iv_flash, "scaleX", 0.9f, 1f);
        ObjectAnimator animator_scaleY = ObjectAnimator.ofFloat(iv_flash, "scaleY", 0.9f, 1f);
        ObjectAnimator animator_alpha = ObjectAnimator.ofFloat(tv_flash, "alpha", 0.3f, 1f);

        set.playTogether(animator_scaleX, animator_scaleY, animator_alpha);
        set.setDuration(2000);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent i = new Intent(SplashMainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }
}
