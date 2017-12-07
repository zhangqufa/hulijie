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

    public void getFirstDataModel(BaseActivity activity, int page, HttpListener listener) {
        String url = AppURL.URL_FIRSTPAGE_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.GET);
        request.add("page", page);
        CallServer.getRequestInstance().add(activity, 0, request, listener, true, false);
    }

    public void getFirstForCategoryDataModel(BaseActivity activity, String cate_id, String keywords,int page, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_CATEGORY_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.GET);
        request.add("cate_id", cate_id);
        request.add("keywords", keywords);
        request.add("page", page);
        CallServer.getRequestInstance().add(activity, 0, request, httpListener, true, true);
    }

    public void getAccessInfo(BaseActivity context, String user_id, String key, HttpListener<JSONObject> listener) {
        String url = AppURL.URL_ACCESS_INFO;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("key", key);
        CallServer.getRequestInstance().add(context, 0, request, listener, true, true);
    }
}
