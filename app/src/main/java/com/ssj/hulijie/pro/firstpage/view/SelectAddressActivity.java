package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.AddressManagerAdapter;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.presenter.AddressManagerPresenter;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerItemDecoration;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/7/5
 */

public class SelectAddressActivity extends BaseActivity {

    public static final int REQUESTCODE = 101;
    private RecyclerView rv_address;
    private AddressManagerAdapter adapter;
    private AddressManagerPresenter presenter;
    private List<AddressItem> lists = new ArrayList<>();


    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new AddressManagerPresenter(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select_address);
        initToolbar();
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        presenter.getAddressPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), "", new BasePresenter.OnUIThreadListener<List<AddressItem>>() {
            @Override
            public void onResult(List<AddressItem> result,int return_code) {
                if (result != null&&result.size()>0) {
                    for (int i  =0;i<result.size();i++) {
                        if (i == 0) {
                            AddressItem addressItem = result.get(i);
                            addressItem.setDefault(true);
                        }
                    }
                    lists = result;
                    adapter.setLists(lists);
                }
            }
        });
    }




    private void initView() {
        rv_address=(RecyclerView)findViewById(R.id.rv_address);
        rv_address.setLayoutManager(new LinearLayoutManager(this));
        rv_address.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        adapter = new AddressManagerAdapter(this);
        rv_address.setAdapter(adapter);

    }


    private void initToolbar() {
        RelativeLayout title_bar_base=(RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "地址管理", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }

    public void addAddress(View view) {
        Intent intent = new Intent(this, AddressActivity.class);
        startActivityForResult(intent, REQUESTCODE);
    }

    public void editAddress(View view) {
        for (AddressItem item : lists) {
            item.setEdit(true);
            item.setDefault(false);
        }
        adapter.setLists(lists);
    }
}
