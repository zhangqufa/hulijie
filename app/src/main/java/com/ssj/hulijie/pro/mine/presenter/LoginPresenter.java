package com.ssj.hulijie.pro.mine.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.LoginItem;
import com.ssj.hulijie.pro.mine.bean.VerifyCode;
import com.ssj.hulijie.pro.mine.model.LoginModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;


/**
 * Created by Administrator on 2017/7/27.
 */

public class LoginPresenter extends BasePresenter<LoginModel> {

    public LoginPresenter(Context context) {
        super(context);
    }

    @Override
    public LoginModel bindModel() {
        return new LoginModel(getContext());
    }


    public void getVerifyCodePresenter(BaseActivity context, String mobile, final OnUIThreadListener<VerifyCode> onUIThreadListener) {
        getModel().getVerifyCode(context, mobile, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    try {
                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {
                            String data = jsonObject.getString("data");
                            VerifyCode item = JSON.parseObject(data, VerifyCode.class);
                            AppLog.Log("verify_code: " + item);
                            onUIThreadListener.onResult(item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                    }

                } else {
                    onUIThreadListener.onResult(null);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }

    public void loginPresenter(BaseActivity context, String mobile, String code, final OnUIThreadListener<LoginItem> onUIThreadListener) {
        getModel().loginModel(context, mobile, code, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    try {
                        AppLog.Log("login_str: " + jsonObject.toString());

                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {
                            String data = jsonObject.getString("data");
                            LoginItem item = JSON.parseObject(data, LoginItem.class);
                            onUIThreadListener.onResult(item);
                        } else {
                            LoginItem itemfail = new LoginItem();
                            String msg = jsonObject.getString("msg");
                            itemfail.setMsg(msg);
                            onUIThreadListener.onResult(itemfail);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                    }

                } else {
                    onUIThreadListener.onResult(null);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }


}
