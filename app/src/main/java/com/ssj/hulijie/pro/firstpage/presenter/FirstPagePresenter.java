package com.ssj.hulijie.pro.firstpage.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.FourpartData;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.model.FirstPageModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2017/6/3.
 */

public class FirstPagePresenter extends BasePresenter<FirstPageModel> {


    public FirstPagePresenter(Context context) {
        super(context);
    }

    @Override
    public FirstPageModel bindModel() {
        return new FirstPageModel(getContext());
    }

    public void getCatetoryPresenter(BaseActivity activity, final OnUIThreadListener<List<ItemFirstPageMainHeaderList>> onUIThreadListener) {
        getModel().getCatetroyModel(activity, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                JSONObject jsonObject = response.get();

                if (jsonObject != null) {

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            String data = jsonObject.getString("data");
                            List<ItemFirstPageMainHeaderList> lists = new ArrayList<>(JSONArray.parseArray(data, ItemFirstPageMainHeaderList.class));
                            onUIThreadListener.onResult(lists);
                            AppLog.Log("catetory: " + lists.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("catetory_exception: " + e.toString());
                    }

                } else {
                    AppLog.Log("catetory is null");
                    onUIThreadListener.onResult(null);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }


    public void getMidFourPresenter(BaseActivity activity, final OnUIThreadListener<List<FourpartData>> onUIThreadListener) {
        getModel().getMidFourModel(activity, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                JSONObject jsonObject = response.get();

                if (jsonObject != null) {

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            String data = jsonObject.getString("data");
                            List<FourpartData> lists = new ArrayList<>(JSONArray.parseArray(data, FourpartData.class));
                            onUIThreadListener.onResult(lists);
                            AppLog.Log("four_model: " + lists.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("four_model: " + e.toString());
                    }

                } else {
                    AppLog.Log("four_model is null");
                    onUIThreadListener.onResult(null);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }


    public void getFirstDataPresenter(BaseActivity activity, int page, final OnUIThreadListener<List<ItemFirstPageMainList>> onUIThreadListener) {
        getModel().getFirstDataModel(activity, page,new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            String data = jsonObject.getString("data");
                            List<ItemFirstPageMainList> lists = new ArrayList<>(JSONArray.parseArray(data, ItemFirstPageMainList.class));
                            onUIThreadListener.onResult(lists);
                            AppLog.Log("firstList: " + lists.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("firstList: " + e.toString());
                    }

                } else {
                    AppLog.Log("firstList is null");
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
