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

    public void getOrderSingPresenter(BaseActivity context, String order_id, String user_id, final OnUIThreadListener<String> onUIThreadListener) {
        getModel().getOrderSignModel(context, order_id,user_id,new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String s = response.get();
                AppLog.Log("去支付：" + s);
                onUIThreadListener.onResult(s);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }

    public void getOrderSignTwoPresenter(BaseActivity context, String user_id, String goods_id, String amount, String mobile, String service_address,
                                         String buyer_name, long service_time, String remark, final OnUIThreadListener<String> onUIThreadListener) {
        getModel().getOrderSignTwoModel(context, user_id, goods_id, amount, mobile, service_address, buyer_name, service_time, remark, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String s = response.get();
                AppLog.Log("");
                onUIThreadListener.onResult(s);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }
}
