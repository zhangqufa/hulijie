package com.ssj.hulijie.pro.firstpage.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.model.BaseModel;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.firstpage.model.AddressManagerModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AddressManagerPresenter extends BasePresenter<AddressManagerModel> {


    public AddressManagerPresenter(Context context) {
        super(context);
    }

    @Override
    public AddressManagerModel bindModel() {
        return new AddressManagerModel(getContext());
    }

    public void getAddressPresenter(BaseActivity context, String user_id, String addr_id, final OnUIThreadListener<List<AddressItem>> onUIThreadListener) {
        getModel().getAddressModel(context, user_id, addr_id, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();

                if (jsonObject != null) {

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            String data = jsonObject.getString("data");
                            List<AddressItem> lists = new ArrayList<>(JSONArray.parseArray(data, AddressItem.class));
                            onUIThreadListener.onResult(lists);
                            AppLog.Log("addressList: " + lists.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("addressList_exception: " + e.toString());
                    }

                } else {
                    AppLog.Log("addressList is null");
                    onUIThreadListener.onResult(null);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                AppLog.Log("addressList is fail");
                onUIThreadListener.onResult(null);
            }
        });
    }

    public void addAddressPresenter(BaseActivity content, String region_name, String address, String phone_mob, String user_id, String addr_id, String key,
        final OnUIThreadListener<List<AddressItem>> onUIThreadListener) {
        getModel().addAddressModel(content, region_name,address,phone_mob,user_id,addr_id,key, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();

                if (jsonObject != null) {
                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            AppLog.Log("address success!!");
                            onUIThreadListener.onResult(null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                    }

                } else {
                    onUIThreadListener.onResult(null);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }
}
