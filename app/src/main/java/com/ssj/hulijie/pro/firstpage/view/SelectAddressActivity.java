package com.ssj.hulijie.pro.firstpage.view;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.AddressManagerAdapter;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.presenter.AddressManagerPresenter;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerItemDecoration;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;
import com.ssj.hulijie.utils.ViewUtil;

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
    private Button addAddress,editAddress;

    private int defaultAddress;

    private ButtonStauts currentStatus = ButtonStauts.EDIT;


    enum ButtonStauts{
        EDIT,FINISH
    }


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
                            defaultAddress = 0;
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
        adapter.setDeleteCallback(callback);
        rv_address.setAdapter(adapter);

        addAddress = (Button) findViewById(R.id.addAddress);
        editAddress = (Button) findViewById(R.id.editAddress);

    }

    private AddressManagerAdapter.AddressDeleteCallback callback    = new AddressManagerAdapter.AddressDeleteCallback<AddressItem>() {

        @Override
        public void deleteCallback(AddressItem addressItem, final int position) {

            showConfirmAlert(addressItem,position);


        }
    };

    private void showConfirmAlert(final AddressItem addressItem,final int position) {
        AlertDialog.Builder builder   = new AlertDialog.Builder(this).setMessage("确定要删除该地址吗?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestDeleteNet(addressItem,position);
            }
        });
        builder.create();
        builder.show();
    }

    private void requestDeleteNet(AddressItem addressItem,final int position) {
        presenter.deleteAddressPresenter(SelectAddressActivity.this, SharedUtil.getPreferStr(SharedKey.USER_ID), addressItem.getAddr_id(), new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result, int return_code) {
                if (return_code == Constant.SUCCESS_CODE) {
                    lists.remove(position);
                    adapter.notifyItemChanged(position);
                }
            }
        });
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


        if (currentStatus == ButtonStauts.EDIT) {

            for (AddressItem item : lists) {
                item.setEdit(true);
                item.setDefault(false);
            }
            adapter.setLists(lists);
            currentStatus = ButtonStauts.FINISH;
            editAddress.setText("完成");
            addAddress.setVisibility(View.GONE);

        } else {

            for(int i = 0 ;i<lists.size();i++) {
                AddressItem item = lists.get(i);
                item.setEdit(false);
                if (i == defaultAddress) {
                    item.setDefault(true);
                }
            }
            adapter.setLists(lists);
            currentStatus = ButtonStauts.EDIT;
            editAddress.setText("编辑");
            addAddress.setVisibility(View.VISIBLE);

        }
    }

}
