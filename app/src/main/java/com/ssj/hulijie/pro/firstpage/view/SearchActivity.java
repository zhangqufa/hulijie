package com.ssj.hulijie.pro.firstpage.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.DensityUtil;
import com.ssj.hulijie.widget.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/4/7
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private FlowLayout hot_search;
    private List<TextView> tvs_hot_search;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        initView();
    }

    private void initView() {
        findViewById(R.id.iv_navigation_back).setOnClickListener(this);
        hot_search = (FlowLayout)findViewById(R.id.hot_search);
        tvs_hot_search = new ArrayList<>();

        for(int i =0;i<5;i++) {
            addHotsearchDataForFlowView("hot_"+i);
        }
    }

    private void addHotsearchDataForFlowView(String str) {
        int ranHeight = DensityUtil.dip2px(this, 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(0, 0, DensityUtil.dip2px(this, 10), 0);
        TextView tv = new TextView(this);
        tv.setPadding(DensityUtil.dip2px(this, 13), 0, DensityUtil.dip2px(this, 13), 0);
        tv.setTextColor(Color.parseColor("#666666"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setText(str);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(1);
        tv.setBackgroundResource(R.drawable.shape_btn_grey_f5f5f5);
        tvs_hot_search.add(tv);  //add onclick event
        hot_search.addView(tv, lp);

        //监听点击
        for (TextView textView : tvs_hot_search) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof TextView) {
                        TextView tv = (TextView) v;
                        goSearchResult(tv.getText().toString());
                    }
                }
            });
        }

    }

    /**
     * 搜索请求
     * @param string
     */
    private void goSearchResult(String string) {
        AppToast.ShowToast(string);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation_back:
                finish();
                break;
        }
    }
}
