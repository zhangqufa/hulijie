package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.TitlebarUtil;



/**
 * Created by Administrator on 2017/5/15.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {
    private TextView order_buy_count;  //购买数量
    private int count = 1;
    private Button order_sub;


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
        initToolbar();
        order_sub = (Button)findViewById(R.id.order_sub);
        findViewById(R.id.order_add).setOnClickListener(this);
        order_sub.setOnClickListener(this);
        order_sub.setClickable(false);
        order_buy_count = (TextView)findViewById(R.id.order_buy_count);
    }

    private void initToolbar() {
        RelativeLayout title_bar_base=(RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "预约下单", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_add:
                count++;
                changeBuyCount();
                break;
            case R.id.order_sub:
                if (count > 1) {
                    count--;
                    changeBuyCount();
                }
                break;
        }
    }

    private void changeBuyCount(){
        if (count > 1) {
           order_sub.setClickable(true);
        } else if (count == 1) {
            order_sub.setClickable(false);
        }
        order_buy_count.setText(count + "");
    }
}
