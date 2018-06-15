package com.ssj.hulijie.pro.mine.view.seller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.presenter.ServicePresenter;
import com.ssj.hulijie.utils.AppToast;

/**
 * Created by Administrator on 2018/6/15.
 */

public class EvaluateSellReplyActivity extends BaseActivity<ServicePresenter> {

    private EditText et_evaluate_content;
    private String order_id;

    @Override
    public ServicePresenter bindPresenter() {
        return new ServicePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_seller_reply);
        order_id = getIntent().getStringExtra("order_id");
        initToolbar();
        initView();
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        title_bar_base.findViewById(R.id.iv_navigation_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar_base.findViewById(R.id.tv_navigation_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReplyEvaluate();
            }
        });
    }

    private void submitReplyEvaluate() {
        String reply = et_evaluate_content.getText().toString();
        if (TextUtils.isEmpty(reply)) {
            AppToast.ShowToast("回复不可为空");
            return;
        }
        presenter.getSellerReplyPresenter(this, order_id, reply, new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }

    private void initView() {
        et_evaluate_content = findViewById(R.id.et_evaluate_content);

    }
}
