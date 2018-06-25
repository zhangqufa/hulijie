package com.ssj.hulijie.pro.firstpage.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.ItemEvaluate;
import com.ssj.hulijie.pro.firstpage.model.EvaluateModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Administrator on 2018/5/29.
 */

public class EvaluatePresenter extends BasePresenter<EvaluateModel> {

    public EvaluatePresenter(Context context) {
        super(context);
    }

    @Override
    public EvaluateModel bindModel() {
        return new EvaluateModel(getContext());
    }

    public void getEvaluatePresenter(BaseActivity context, int page, String id, int type, final OnUIThreadListener<ItemEvaluate> onUIThreadListener) {
        getModel().getEvaluateModel(context, page, id, type, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                try {
                    if (jsonObject != null) {
                        AppLog.Log("评论：" + jsonObject.toString());

                        int success = jsonObject.getInteger("code");
                        if (Constant.SUCCESS_CODE == success) {
                            ItemEvaluate itemEvaluate = JSON.parseObject(jsonObject.toString(), ItemEvaluate.class);
                            onUIThreadListener.onResult(itemEvaluate);
                        }
                    } else {
                        onUIThreadListener.onResult(null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
