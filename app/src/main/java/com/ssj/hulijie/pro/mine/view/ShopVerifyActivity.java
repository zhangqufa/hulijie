package com.ssj.hulijie.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.TitlebarUtil;


/**
 * Created by Administrator on 2017/7/16.
 */

public class ShopVerifyActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shop_verify);
        initToolBar();
        initView();
    }

    private void initView() {
        findViewById(R.id.card_verify).setOnClickListener(this);
        findViewById(R.id.company_verify).setOnClickListener(this);
    }

    private void initToolBar() {
        RelativeLayout title_bar_base=(RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "商家认证", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }


    @Override
    public void onClick(View view) {
        Intent intent =null;

        switch (view.getId()) {
            case R.id.card_verify:
                intent = new Intent(this, CardVerifyActivity.class);
                break;

            case R.id.company_verify:
                intent = new Intent(this, CompanyVerifyActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            CityGridItem item = data.getParcelableExtra("event");
//            if (item != null) {
//                shopin_addr.setText(item.getCity());
//
//            }
//        }
    }
}
