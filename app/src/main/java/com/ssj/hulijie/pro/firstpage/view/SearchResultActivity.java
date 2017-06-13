package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageMainListAdapter;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.utils.AppToast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ssj.hulijie.base.HljAppliation.context;


/**
 * Created by Administrator on 2017/5/15.
 */

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {
    private TextView nav_center_title;
    private FirstPageMainListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_result);


        initView();

    }

    private void initView() {

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);

        String key = getIntent().getStringExtra("key");
        nav_center_title = (TextView) findViewById(R.id.nav_center_title);
        nav_center_title.setText(key);

        //init RecyclerView
       RecyclerView search_result_rv = (RecyclerView) findViewById(R.id.search_result_rv);
        search_result_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FirstPageMainListAdapter(context);
        search_result_rv.setAdapter(adapter);
        adapter.addDatas(getData());
    }

    /**
     * 测试数据  首页主数据
     *
     * @return
     */
    private List<ItemFirstPageMainList> getData() {
       List<ItemFirstPageMainList> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ItemFirstPageMainList item = new ItemFirstPageMainList();
            item.setName("title_"+i);
            item.setTxt("sub_title_"+i);
            double random = Math.random();
            DecimalFormat df = new DecimalFormat("0.00");
            item.setPrice(df.format(random));
            item.setPic(FirstPageFrament.img[i]);
            data.add(item);
        }
        return data;
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.share:
                AppToast.ShowToast("分享");
                break;
        }
    }
}
