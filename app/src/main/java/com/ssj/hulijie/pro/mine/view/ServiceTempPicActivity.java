package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.widget.ImageView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;

/**
 * Created by vic_zhang .
 * on 2017/8/3
 */

public class ServiceTempPicActivity extends BaseActivity {

    public static final int MINE_SERIVCE = 1000;
    public static final int MINE_ORDER = 1001;
    public static final int MINE_INCOME = 1002;
    public static final int MINE_EVALUATE = 1003;
    public static final int MINE_NAME_VERIFY = 1004;
    public static final int MINE_USUALLY_PROBLEM = 1005;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_serivce_temp_pic);
        ImageView temp_tv = (ImageView) findViewById(R.id.temp_iv);
        int flag = getIntent().getIntExtra("flag", -1);
        int picRes = -1;
        switch (flag
                ) {
            case MINE_SERIVCE:
                break;
            case MINE_ORDER:
                picRes = R.mipmap.mine_order;
                break;
            case MINE_INCOME:
                picRes = R.mipmap.mine_income;
                break;
            case MINE_EVALUATE:
                picRes = R.mipmap.mine_evaluate;
                break;
            case MINE_NAME_VERIFY:

                break;
            case MINE_USUALLY_PROBLEM:

                break;
        }
        if (picRes != -1) {
            temp_tv.setImageResource(picRes);
        }

    }
}
