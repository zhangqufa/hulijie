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

import static com.ssj.hulijie.R.id.mobile;

/**
 * Created by Administrator on 2017/7/27.
 */

public class LoginModel extends BaseModel {
    public LoginModel(Context context) {
        super(context);
    }

    public void getVerifyCode(BaseActivity context, String mobile, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_GET_VERIFY_CODE;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.GET);
        request.add("mobile", mobile);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }

    public void loginModel(BaseActivity context, String mobile,String code, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_LOGIN;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("mobile", mobile);
        request.add("code", code);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }



}
