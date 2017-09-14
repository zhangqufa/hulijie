package com.ssj.hulijie.pro.firstpage.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.model.SearchModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/9/8.
 */

public class SearchPresenter extends BasePresenter<SearchModel> {

    public SearchPresenter(Context context) {
        super(context);
    }

    @Override
    public SearchModel bindModel() {
        return new SearchModel(getContext());
    }

    public void getHotKeysPresenter(BaseActivity context, final OnUIThreadListener<List<String>> onUIThreadListener) {
        getModel().getHotSearchKey(context, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("热门搜索:" + jsonObject.toString());
                    try {
                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {
                            String data = jsonObject.getString("data");
                            List<String> lists = new ArrayList<>(JSONArray.parseArray(data, String.class));
                            onUIThreadListener.onResult(lists);
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
