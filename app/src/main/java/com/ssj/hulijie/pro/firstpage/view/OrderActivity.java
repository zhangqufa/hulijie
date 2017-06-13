package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.view.View;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;

/**
 * Created by Administrator on 2017/5/15.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_progress);
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
