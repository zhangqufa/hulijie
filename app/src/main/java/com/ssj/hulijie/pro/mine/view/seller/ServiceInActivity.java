package com.ssj.hulijie.pro.mine.view.seller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.ItemServiceMoneyInfo;
import com.ssj.hulijie.pro.mine.presenter.ServicePresenter;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

/**
 * @author vic_zhang .
 *         on 2018/5/18
 */

public class ServiceInActivity extends BaseActivity<ServicePresenter> {

    private TextView tv_yinli;
    private TextView tv_frozen;
    private TextView tv_draw;
    private TextView tv_can_draw;

    @Override
    public ServicePresenter bindPresenter() {
        return new ServicePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_server_in);

        initToolbar();
        initView();
        initData();
    }

    private void initView() {
        tv_yinli = (TextView) findViewById(R.id.tv_yinli);
        tv_frozen = (TextView) findViewById(R.id.tv_frozen);
        tv_draw = (TextView) findViewById(R.id.tv_draw);
        tv_can_draw = (TextView) findViewById(R.id.tv_can_draw);
    }

    private void initData() {
        presenter.getServiceMoneyInfoPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), new BasePresenter.OnUIThreadListener<ItemServiceMoneyInfo>() {
            @Override
            public void onResult(ItemServiceMoneyInfo result) {
                if (result != null) {
                    updateUi(result);
                }
            }
        });
    }

    /**
     * 更新界面
     *
     * @param result
     */
    private void updateUi(ItemServiceMoneyInfo result) {
        if (!TextUtils.isEmpty(result.getAdd())) {
            tv_yinli.setText(result.getAdd());
        }
        tv_frozen.setText(result.getFrozen() + "元");
        tv_draw.setText(result.getDraw() + "元");
        tv_can_draw.setText(result.getCan_draw() + "元");
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "我的收入", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);

    }


}
