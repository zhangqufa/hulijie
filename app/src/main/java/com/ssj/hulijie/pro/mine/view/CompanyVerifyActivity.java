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

import static com.ssj.hulijie.R.id.shopin_addr;

/**
 * Created by Administrator on 2017/9/3.
 */

public class CompanyVerifyActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 100;
    private TextView addr_text;
    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_company_verify);
        initToolBar();
        initView();
    }

    private void initView() {
        findViewById(R.id.addr).setOnClickListener(this);
        addr_text=(TextView) findViewById(R.id.addr_text);
    }

    private void initToolBar() {
        RelativeLayout title_bar_base=(RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "企业/个体工商户注册", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addr:
                Intent intent = new Intent(this, ProvincesCityAddressActivity.class);
                startActivityForResult(intent,REQUEST_CODE);

                break;
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            CityGridItem item = data.getParcelableExtra("event");
            if (item != null) {
                addr_text.setText(item.getCity());

            }
        }
    }
}
