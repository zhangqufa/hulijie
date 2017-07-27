package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

/**
 * Created by Administrator on 2017/7/27.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);

        initToolbar();
        initView();
    }

    private void initView() {

        findViewById(R.id.btn_logout).setOnClickListener(this);
    }

    private void initToolbar() {

        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "设置", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        },null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                SharedUtil.setPreferBool(SharedKey.USER_LOGINED, false);
                finish();
                break;
        }
    }
}
