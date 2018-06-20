package com.ssj.hulijie.pro.msg.model;

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
 * Created by Administrator on 2018/6/20.
 */

public class MsgModel extends BaseModel {

    public MsgModel(Context context) {
        super(context);
    }

    /**
     * 活动消息列表
     * @param context
     * @param page
     * @param httpListener
     */
    public void getMsgHuoDongModel(BaseActivity context, int page, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_MSG_NEWS;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("page", page);
        request.add("pagesize", 10);
        CallServer.getRequestInstance().add(context,0,request,httpListener,true,true);

    }
    /**
     * 系统消息 列表
     * @param context
     * @param page
     * @param httpListener
     */
    public void getMsgSystemModel(BaseActivity context,String user_id,  int page, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_SYSTEM_MSG;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("page", page);
        request.add("user_id", user_id);
        request.add("pagesize", 10);
        CallServer.getRequestInstance().add(context,0,request,httpListener,true,true);

    }
}
