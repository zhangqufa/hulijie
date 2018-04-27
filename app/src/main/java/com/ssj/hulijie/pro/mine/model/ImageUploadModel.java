package com.ssj.hulijie.pro.mine.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.CallServer;
import com.ssj.hulijie.nohttp.FastJsonRequest;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.model.BaseModel;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppURL;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.tools.ImageLocalLoader;

/**
 * @author vic_zhang .
 *         on 2018/4/20
 */

public class ImageUploadModel extends BaseModel {
    public ImageUploadModel(Context context) {
        super(context);
    }


    public void onImageUploadModel(BaseActivity context, OnUploadListener mOnUploadListener, HttpListener<JSONObject> httpListener, String... paths) {
        Request<JSONObject> request = new FastJsonRequest(AppURL.URL_PIC_UPLOAD, RequestMethod.POST);
        try {
            int length = paths.length;
            for (int i = 0; i < length; i++) {
                AppLog.Log("paths[i]:"+paths[i]);

                Bitmap file2 = ImageLocalLoader.getInstance().readImage(paths[i], 720, 1280);
                BasicBinary binary = new BitmapBinary(file2, "abc"+i+".png");
                binary.setUploadListener(i, mOnUploadListener);
                request.add("image" + i, binary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }
}
