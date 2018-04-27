package com.ssj.hulijie.pro.mine.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.ItemImageUpload;
import com.ssj.hulijie.pro.mine.model.ImageUploadModel;
import com.ssj.hulijie.utils.AppLog;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * @author vic_zhang .
 *         on 2018/4/20
 */

public class ImageUpladPresenter extends BasePresenter<ImageUploadModel> {

    public ImageUpladPresenter(Context context) {
        super(context);
    }

    @Override
    public ImageUploadModel bindModel() {
        return new ImageUploadModel(getContext());
    }


    public void onImageUploadPresenter(BaseActivity context, OnUploadListener mOnUploadListener
            , final OnUIThreadListener<ItemImageUpload> onUIThreadListener, String... paths) {
        getModel().onImageUploadModel(context, mOnUploadListener, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject s = response.get();
                if (s != null) {
                    try {
                        AppLog.Log("图片上传成功" + s.toString());
                        ItemImageUpload itemImageUpload = JSON.parseObject(s.toString(), ItemImageUpload.class);
                        onUIThreadListener.onResult(itemImageUpload);
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
        }, paths);
    }

}
