package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.TitlebarUtil;

/**
 * Created by vic_zhang .
 * on 2017/8/4
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {
    private DetailServiceItem detail;
    private ImageView wechat_select, alipay_select;
    private PayStatus currentPayStatus = PayStatus.WECHAT;
    private Button btn_pay;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wechat_select:
                currentPayStatus = PayStatus.WECHAT;
                wechat_select.setImageResource(R.mipmap.address_sel_seleted);
                alipay_select.setImageResource(R.mipmap.address_sel_unseleted);
                break;
            case R.id.alipay_select:
                currentPayStatus = PayStatus.ALIPAY;
                wechat_select.setImageResource(R.mipmap.address_sel_unseleted);
                alipay_select.setImageResource(R.mipmap.address_sel_seleted);
                break;
            case R.id.btn_pay:
                if (currentPayStatus == PayStatus.ALIPAY) {

                    AppToast.ShowToast("当前为支付宝支付");
                } else {
                    AppToast.ShowToast("当前为微信支付");
                }

                break;
        }
    }

    enum PayStatus{
        WECHAT,ALIPAY
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detail = getIntent().getParcelableExtra("detail");
        setContentView(R.layout.act_pay);
        initToolbar();
        initView();

    }

    private void initView() {
        btn_pay = (Button) findViewById(R.id.btn_pay);
        wechat_select = (ImageView) findViewById(R.id.wechat_select);
        alipay_select = (ImageView) findViewById(R.id.alipay_select);
        btn_pay.setOnClickListener(this);
        alipay_select.setOnClickListener(this);
        wechat_select.setOnClickListener(this);
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "支付收银台", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }
}
