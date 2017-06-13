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
 * Created by Administrator on 2017/6/12.
 */

public class AllCatetoryModel extends BaseModel {


    public AllCatetoryModel(Context context) {
        super(context);
    }


    public void getAllCatetoryModel(BaseActivity context, HttpListener httpListener) {
        String url = AppURL.URL_CATETORY;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);

    }
}
