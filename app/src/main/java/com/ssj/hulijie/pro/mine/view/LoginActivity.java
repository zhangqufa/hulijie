package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.TitlebarUtil;

/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginActivity extends BaseActivity {
    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initView();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        RelativeLayout toolbar_base =  (RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, toolbar_base, true, "登录", android.R.color.white, 0, R.drawable.select_close, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }
}
