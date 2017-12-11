package com.ssj.hulijie.pro.mine.model;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.CallServer;
import com.ssj.hulijie.nohttp.FastJsonRequest;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.model.BaseModel;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;

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

    public void getOrderSignModel(BaseActivity context, String order_id, String user_id, HttpListener<String> httpListener) {
        String url = AppURL.URL_ORDER_PAY;
        Request<String> request = new StringRequest(url, RequestMethod.POST);
        request.add("order_id", order_id);
        request.add("user_id", user_id);
        CallServer.getRequestInstance().add(context,0,request,httpListener,true,true);
    }

    public void getOrderSignTwoModel(BaseActivity context, String user_id, String goods_id, String amount, String mobile, String service_address,
                                     String buyer_name, long service_time, String remark, HttpListener<String> httpListener){
        String url = AppURL.URL_ORDER_ORDER;
        Request<String> req =NoHttp.createStringRequest(url, RequestMethod.POST);
        req.add("user_id", user_id);
        req.add("goods_id", goods_id);
        req.add("amount", amount);
        req.add("mobile", mobile);
        req.add("service_address", service_address);
        req.add("buyer_name",buyer_name);
        req.add("service_time", service_time);
        req.add("remark", remark);
        CallServer.getRequestInstance().add(context,0,req,httpListener,true,true);
    }


}
