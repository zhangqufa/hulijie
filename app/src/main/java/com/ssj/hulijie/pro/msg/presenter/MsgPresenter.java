package com.ssj.hulijie.pro.msg.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.msg.bean.ItemMsgHuoDong;
import com.ssj.hulijie.pro.msg.model.MsgModel;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Administrator on 2018/6/20.
 */

public class MsgPresenter extends BasePresenter<MsgModel> {

    public MsgPresenter(Context context) {
        super(context);
    }

    @Override
    public MsgModel bindModel() {
        return new MsgModel(getContext());
    }

    /**
     * 活动消息列表
     *
     * @param context
     * @param page
     */
    public void getMsgHuoDongPresenter(BaseActivity context, int page, final OnUIThreadListener<ItemMsgHuoDong> onUIThreadListener) {
        getModel().getMsgHuoDongModel(context, page, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("活动消息 列表： " + jsonObject.toString());
                    try {
                        ItemMsgHuoDong itemMsgHuoDong = JSON.parseObject(jsonObject.toString(), ItemMsgHuoDong.class);
                        onUIThreadListener.onResult(itemMsgHuoDong);
                    } catch (Exception e) {
                        onUIThreadListener.onResult(null);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }

    /**
     * 系统消息列表
     *
     * @param context
     * @param page
     */
    public void getMsgSystemPresenter(BaseActivity context, String user_id,int page, final OnUIThreadListener<ItemMsgHuoDong> onUIThreadListener) {
        getModel().getMsgSystemModel(context,user_id, page, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("系统消息 列表： " + jsonObject.toString());
                    try {
                        ItemMsgHuoDong itemMsgHuoDong = JSON.parseObject(jsonObject.toString(), ItemMsgHuoDong.class);
                        onUIThreadListener.onResult(itemMsgHuoDong);
                    } catch (Exception e) {
                        onUIThreadListener.onResult(null);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }
}
