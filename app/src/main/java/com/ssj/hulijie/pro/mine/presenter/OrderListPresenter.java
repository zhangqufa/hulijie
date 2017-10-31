package com.ssj.hulijie.pro.mine.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.pro.mine.model.OrderListModel;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Administrator on 2017/10/9.
 */

public class OrderListPresenter extends BasePresenter<OrderListModel> {
    public OrderListPresenter(Context context) {
        super(context);
    }

    @Override
    public OrderListModel bindModel() {
        return new OrderListModel(getContext());
    }

    public void getOrderListPresenter(BaseActivity context, String user_id, int page, int type, final OnUIThreadListener<ItemOrderResp> onUIThreadListener) {
        getModel().getOrderListModel(context, user_id, page, type, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("订单列表：" + jsonObject.toString());
                    try {
                        ItemOrderResp itemOrderList = JSON.parseObject(jsonObject.toString(), ItemOrderResp.class);
                        onUIThreadListener.onResult(itemOrderList);
                    } catch (Exception e) {
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
