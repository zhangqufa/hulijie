package com.ssj.hulijie.pro.firstpage.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.firstpage.model.AllCatetoryModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class AllCatetoryPresenter extends BasePresenter<AllCatetoryModel> {


    public AllCatetoryPresenter(Context context) {
        super(context);
    }

    @Override
    public AllCatetoryModel bindModel() {
        return new AllCatetoryModel(getContext());
    }

    public void getAllcatetoryPresenter(BaseActivity context, final OnUIThreadListener<List<CatetoryItem>> onUIThreadListener) {
        getModel().getAllCatetoryModel(context, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();

                if (jsonObject != null) {

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            String data = jsonObject.getString("data");
                            List<CatetoryItem> lists = new ArrayList<>(JSONArray.parseArray(data, CatetoryItem.class));
                            onUIThreadListener.onResult(lists,code);
                            AppLog.Log("allcatetory: " + lists.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null,0);
                        AppLog.Log("allcatetory_exception: " + e.toString());
                    }

                } else {
                    AppLog.Log("allcatetory is null");
                    onUIThreadListener.onResult(null,0);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null,0);
            }
        });
    }


}
