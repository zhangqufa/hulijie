package com.ssj.hulijie.pro.mine.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.ItemServiceOrderList;
import com.ssj.hulijie.pro.mine.model.ServiceModle;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ServicePresenter extends BasePresenter<ServiceModle> {

    public ServicePresenter(Context context) {
        super(context);
    }

    @Override
    public ServiceModle bindModel() {
        return new ServiceModle(getContext());
    }

    public void getAcceptOrderListModel(BaseActivity content, int page, int pagesize, final OnUIThreadListener<ItemServiceOrderList> onUIThreadListener) {

        getModel().getAcceptOrderListModel(content, page, pagesize, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();

                if (jsonObject != null) {
                    AppLog.Log("可接单的列表：" + jsonObject.toString());
                    try {
                        ItemServiceOrderList itemServiceOrderList = JSON.parseObject(jsonObject.toString(), ItemServiceOrderList.class);
                        onUIThreadListener.onResult(itemServiceOrderList);
                    } catch (Exception e
                            ) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }
}