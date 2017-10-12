package com.ssj.hulijie.pro.mine.model;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.CallServer;
import com.ssj.hulijie.nohttp.FastJsonRequest;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.model.BaseModel;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.AppURL;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

/**
 * Created by Administrator on 2017/10/9.
 */

public class OrderListModel extends BaseModel {

    public OrderListModel(Context context) {
        super(context);
    }

    public void getOrderListModel(BaseActivity context, String user_id, int page, int type, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_ORDER_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("page", page);
        request.add("type", type);
        CallServer.getRequestInstance().add(context,0,request,httpListener,true,true);
    }
}
