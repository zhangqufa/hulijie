package com.ssj.hulijie.pro.mine.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.ItemOrderList;
import com.ssj.hulijie.pro.mine.model.OrderListModel;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

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

    public void getOrderListPresenter(BaseActivity context, String user_id, int page, int type, OnUIThreadListener<List<ItemOrderList>> onUIThreadListener) {
        getModel().getOrderListModel(context, user_id, page, type, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("订单列表：" + jsonObject.toString());


                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }
        });
    }
}
