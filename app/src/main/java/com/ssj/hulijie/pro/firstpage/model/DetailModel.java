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
 * Created by Administrator on 2017/6/17.
 */

public class DetailModel extends BaseModel {


    public DetailModel(Context context) {
        super(context);
    }


    public void getDetailModel(BaseActivity context,String good_id, HttpListener httpListener) {
        String url = AppURL.URL_SERVICE_DETAIL;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.GET);
        request.add("id", good_id);
        CallServer.getRequestInstance().add(context,0,request,httpListener,true,true);
    }



}
