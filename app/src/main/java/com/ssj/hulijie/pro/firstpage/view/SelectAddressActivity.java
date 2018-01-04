package com.ssj.hulijie.pro.firstpage.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.AddressManagerAdapter;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.presenter.AddressManagerPresenter;
import com.ssj.hulijie.pro.mine.view.LoginActivity;
import com.ssj.hulijie.pro.mine.view.MineFragment;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.Constant;
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
    private String mine_to_select;


    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new AddressManagerPresenter(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select_address);
        mine_to_select = getIntent().getStringExtra(MineFragment.MINE_TO_SELECTADRESS);
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
            public void onResult(List<AddressItem> result) {
                if (result != null && result.size() > 0) {
                    lists = result;
                    adapter.setLists(lists);
                }
            }
        });
    }


    private void initView() {
        rv_address = (RecyclerView) findViewById(R.id.rv_address);
        rv_address.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressManagerAdapter(this);
        adapter.setDeleteCallback(deleteCallback);
        if (TextUtils.isEmpty(mine_to_select) && !MineFragment.MINE_TO_SELECTADRESS.equals(mine_to_select)) {
            adapter.setSelectCallBack(selectCallBack);
        }
        adapter.setDefaultCallback(defaultCallback);
        rv_address.setAdapter(adapter);


    }

    private AddressManagerAdapter.AddressSelectCallBack selectCallBack = new AddressManagerAdapter.AddressSelectCallBack<AddressItem>() {
        @Override
        public void onAddressSelectCallBack(AddressItem o, int position) {
            Intent intent = new Intent();
            intent.putExtra("addressItem", o);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private AddressManagerAdapter.AddressDeleteCallback deleteCallback = new AddressManagerAdapter.AddressDeleteCallback<AddressItem>() {

        @Override
        public void deleteCallback(AddressItem addressItem, final int position) {

            showConfirmAlert(addressItem, position);


        }
    };

    private AddressManagerAdapter.AddressSetDefaultCallback defaultCallback = new AddressManagerAdapter.AddressSetDefaultCallback<AddressItem>() {
        @Override
        public void onAddressSetDefaultCallBack(AddressItem o, int postion) {
            requestDefaultNet(o, postion);
        }
    };

    private void requestDefaultNet(AddressItem o, int postion) {
        presenter.addAddressPresenter(this
                , o.getRegion_name()
                , o.getAddress()
                , SharedUtil.getPreferStr(SharedKey.USER_MOBILE)
                , SharedUtil.getPreferStr(SharedKey.USER_ID)
                , o.getAddr_id()
                , 1
                , o.getLatitude()
                , o.getLongitude()
                , new BasePresenter.OnUIThreadListener<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        if (result) {
                            initData();

                        } else {
                            AppToast.ShowToast("提交失败！");
                        }
                    }
                });

    }

    private void showConfirmAlert(final AddressItem addressItem, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("确定要删除该地址吗?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestDeleteNet(addressItem, position);
            }
        });
        builder.create();
        builder.show();
    }


    private void requestDeleteNet(AddressItem addressItem, final int position) {
        presenter.deleteAddressPresenter(SelectAddressActivity.this, SharedUtil.getPreferStr(SharedKey.USER_ID), addressItem.getAddr_id(), new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
//                    lists.remove(position);
//                    adapter.notifyItemChanged(position);
                if (result != null && result)
                    initData();
            }
        });
    }


    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "地址管理", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }

    public void addAddress(View view) {
        Intent intent = null;
        if (!SharedUtil.getPreferBool(SharedKey.USER_LOGINED, false)) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        intent = new Intent(this, AddressActivity.class);
        startActivityForResult(intent, REQUESTCODE);
    }


}
