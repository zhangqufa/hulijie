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
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ServiceModle extends BaseModel {

    public ServiceModle(Context context) {
        super(context);
    }

    public void getAcceptOrderListModel(BaseActivity content, int page, int pagesize, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_ORDER_SERVICE_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("page", page);
        request.add("pagesize", pagesize);
        CallServer.getRequestInstance().add(content, 0, request, httpListener, true, false);

    }
}
