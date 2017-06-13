package com.ssj.hulijie.pro.firstpage.model;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.CallServer;
import com.ssj.hulijie.nohttp.FastJsonRequest;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.model.BaseModel;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.AppURL;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

/**
 * Created by Administrator on 2017/6/3.
 */

public class FirstPageModel extends BaseModel {
    public FirstPageModel(Context context) {
        super(context);
    }


    public void getCatetroyModel(BaseActivity activity, HttpListener listener) {
        String url = AppURL.URL_FIRST_PAGE_CATETORY;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        CallServer.getRequestInstance().add(activity, 0, request, listener, true, true);
    }


    public void getMidFourModel(BaseActivity activity, HttpListener listener) {
        String url = AppURL.URL_FOUR_MODEL;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        CallServer.getRequestInstance().add(activity, 0, request, listener, true, true);
    }

    public void getFirstDataModel(BaseActivity activity,int page, HttpListener listener) {
        String url = AppURL.URL_FIRSTPAGE_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("page", page);
        CallServer.getRequestInstance().add(activity, 0, request, listener, true, true);
    }
}
