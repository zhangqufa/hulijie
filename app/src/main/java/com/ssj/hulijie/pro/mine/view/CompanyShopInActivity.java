package com.ssj.hulijie.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.CityGridItem;
import com.ssj.hulijie.utils.TitlebarUtil;


/**
 * Created by Administrator on 2017/7/16.
 */

public class CompanyShopInActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 100;
    private TextView shopin_addr;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shop_in);
        initToolBar();
        initView();
    }

    private void initView() {
        findViewById(R.id.shopin_address_select).setOnClickListener(this);
        shopin_addr = (TextView) findViewById(R.id.shopin_addr);
    }

    private void initToolBar() {
        RelativeLayout title_bar_base=(RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "商家入驻", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopin_address_select:
                Intent intent = new Intent(this, ProvincesCityAddressActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            CityGridItem item = data.getParcelableExtra("event");
            if (item != null) {
                shopin_addr.setText(item.getCity());

            }
        }
    }
}
