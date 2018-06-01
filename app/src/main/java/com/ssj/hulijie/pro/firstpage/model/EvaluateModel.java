package com.ssj.hulijie.pro.firstpage.model;

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
 * Created by Administrator on 2018/5/29.
 */

public class EvaluateModel extends BaseModel {

    public EvaluateModel(Context context) {
        super(context);
    }

    public void getEvaluateModel(BaseActivity context, int page, String id, int type,HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_EVALUATE;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.GET);
        request.add("page", page);
        request.add("pagesize",10);
        request.add("id", id);
        request.add("type", type);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);

    }
}
