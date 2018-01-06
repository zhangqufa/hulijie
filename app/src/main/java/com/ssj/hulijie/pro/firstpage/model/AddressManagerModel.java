package com.ssj.hulijie.pro.firstpage.model;

import android.content.Context;
import android.webkit.URLUtil;

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
 * Created by Administrator on 2017/7/11.
 */

public class AddressManagerModel extends BaseModel {


    public AddressManagerModel(Context context) {
        super(context);
    }


    public void getAddressModel(BaseActivity content,String user_id,String addr_id, HttpListener listener){
        String url = AppURL.URL_ADDRESS_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("addr_id", addr_id);
        CallServer.getRequestInstance().add(content,0,request,listener,true,true);

    }

    public void addAddressModel(BaseActivity content, String region_name
            , String address, String phone_mob, String user_id, String addr_id,int default_addr,
                                double latitude,double longitude, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_EDIT_ADDRESS;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("region_name", region_name);
        request.add("address", address);
        request.add("phone_mob", phone_mob);
        request.add("user_id", user_id);
        request.add("addr_id", addr_id);
        request.add("default_addr", default_addr);
        request.add("latitude", latitude);
        request.add("longitude", longitude);
        CallServer.getRequestInstance().add(content,0,request,httpListener,true,true);

    }
    public void deleteddressModel(BaseActivity content, String user_id, String addr_id,HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_DELETE_ADDRESS;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("addr_id", addr_id);
        CallServer.getRequestInstance().add(content,0,request,httpListener,true,true);

    }

}
