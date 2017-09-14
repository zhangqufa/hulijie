package com.ssj.hulijie.pro.firstpage.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceAndEvaluateItem;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceEvaluate;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.EvaluateItem;
import com.ssj.hulijie.pro.firstpage.model.DetailModel;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailPresenter extends BasePresenter<DetailModel> {


    public DetailPresenter(Context context) {
        super(context);
    }

    @Override
    public DetailModel bindModel() {
        return new DetailModel(getContext());
    }

    public void getDetailPresenter(BaseActivity context, String good_id, final OnUIThreadListener<DetailServiceAndEvaluateItem> onUIThreadListener) {
        getModel().getDetailModel(context, good_id, new HttpListener<JSONObject>(){
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("服务详情_original: " + jsonObject.toString());
                    try {
                        int code = jsonObject.getIntValue("code");
                        if (code == Constant.SUCCESS_CODE) {
                            String data = jsonObject.getString("data");
                            AppLog.Log("detail_info: "+data);
//                             data="{\n" +
//                                    "    \"info\": {\n" +
//                                    "        \"goods_id\": \"3230\",\n" +
//                                    "        \"name\": \"空调清洗\",\n" +
//                                    "        \"add_time\": \"1487788526\",\n" +
//                                    "        \"cate_id\": \"1981\",\n" +
//                                    "        \"cate_name\": \"家政空调清洗\",\n" +
//                                    "        \"default_image\": \"/data/files/store_431/goods_94/small_201702231034546855.jpg\",\n" +
//                                    "        \"description\": \"/ueditor/php/upload/image/20170223/1487817297187612.jpg\",\n" +
//                                    "        \"last_update\": \"1487788526\",\n" +
//                                    "        \"price\": \"170.00\"\n" +
//                                    "    },\n" +
//                                    "    \"evaluate\": [\n" +
//                                    "        {\n" +
//                                    "            \"evaluation\": \"5\",\n" +
//                                    "            \"comment\": \"哈哈哈哈啊哈，测试数据\",\n" +
//                                    "            \"subscore1\": \"5\",\n" +
//                                    "            \"subscore2\": \"5\",\n" +
//                                    "            \"subscore3\": \"5\",\n" +
//                                    "            \"images\": \"../uploads/20170617//5944fc3d0a99b.jpg\",\n" +
//                                    "            \"commentdate\": \"1497664447\"\n" +
//                                    "        }\n" +
//                                    "    ]\n" +
//                                    "}";
                            JSONObject data_temp = JSONObject.parseObject(data);
                            String info = data_temp.getString("info");
                            String evaluate = data_temp.getString("evaluate");
                            DetailServiceItem detailServiceItem1 = JSON.parseObject(info, DetailServiceItem.class);
                            DetailServiceEvaluate evaluateItems = JSON.parseObject(evaluate, DetailServiceEvaluate.class);
                            DetailServiceAndEvaluateItem item = new DetailServiceAndEvaluateItem();
                            item.setDetail(detailServiceItem1);
                            item.setEvaluate(evaluateItems);
                            onUIThreadListener.onResult(item);
                            AppLog.Log("服务详情: " + item.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("服务详情: " + e.toString());
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
