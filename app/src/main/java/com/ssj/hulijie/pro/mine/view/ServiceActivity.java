package com.ssj.hulijie.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.adapter.ServiceAdapter;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/8/3
 */

public class ServiceActivity extends BaseActivity {
    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_service);
        initToolbar();
        initView();
    }

    private void initView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        ServiceAdapter adapter = new ServiceAdapter(this,getData());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
        adapter.setListener(itemOnclickListener);
    }
    private ServiceAdapter.ItemOnclickListener itemOnclickListener = new ServiceAdapter.ItemOnclickListener() {
        @Override
        public void onItemClickLisener(int position) {
            Intent intent = null;
            switch (position) {
                case 0:

                    break;
                case 1:
                    intent = new Intent(ServiceActivity.this, ServiceTempPicActivity.class);
                    intent.putExtra("flag", ServiceTempPicActivity.MINE_ORDER);
                    break;
                case 2:
                    intent = new Intent(ServiceActivity.this, ServiceTempPicActivity.class);
                    intent.putExtra("flag", ServiceTempPicActivity.MINE_INCOME);
                    break;
                case 3:
                    intent = new Intent(ServiceActivity.this, ServiceTempPicActivity.class);
                    intent.putExtra("flag", ServiceTempPicActivity.MINE_EVALUATE);
                    break;
                case 4:

                    break;
                case 5:

                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    };

    private List<String> getData() {

        List<String> lists = new ArrayList<>();
        lists.add("我的服务");
        lists.add("我的订单");
        lists.add("我的收入");
        lists.add("我的评价");
        lists.add("实名认证");
        lists.add("常见问题");
        return lists;
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "我是服务者", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }
}
