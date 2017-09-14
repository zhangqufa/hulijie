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
 * Created by Administrator on 2017/9/8.
 */

public class SearchModel extends BaseModel {
    public SearchModel(Context context) {
        super(context);
    }

    public void getHotSearchKey(BaseActivity context, HttpListener<JSONObject> listener) {
        String url = AppURL.URL_HOT_KEYS;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.GET);
        CallServer.getRequestInstance().add(context,0,request,listener,true,true);
    }
}
