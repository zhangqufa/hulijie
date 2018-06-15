package com.ssj.hulijie.pro.mine.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.pro.mine.bean.ItemServiceInMoneyDetail;
import com.ssj.hulijie.pro.mine.bean.ItemServiceMoneyInfo;
import com.ssj.hulijie.pro.mine.bean.ItemServiceOrderList;
import com.ssj.hulijie.pro.mine.model.ServiceModle;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ServicePresenter extends BasePresenter<ServiceModle> {

    public ServicePresenter(Context context) {
        super(context);
    }

    @Override
    public ServiceModle bindModel() {
        return new ServiceModle(getContext());
    }

    public void getAcceptOrderListModel(BaseActivity content, int page, int pagesize, final OnUIThreadListener<ItemServiceOrderList> onUIThreadListener) {

        getModel().getAcceptOrderListModel(content, page, pagesize, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();

                if (jsonObject != null) {
                    AppLog.Log("可接单的列表：" + jsonObject.toString());
                    try {
                        ItemServiceOrderList itemServiceOrderList = JSON.parseObject(jsonObject.toString(), ItemServiceOrderList.class);
                        onUIThreadListener.onResult(itemServiceOrderList);
                    } catch (Exception e
                            ) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(null);
            }
        });
    }

    public void serviceGetOrderPresenter(BaseActivity activity, String order_id, String user_id, final OnUIThreadListener<Boolean> onUIThreadListener) {
        getModel().serviceGetOrderModel(activity, order_id, user_id, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家抢单： " + jsonObject.toString());

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {
                            onUIThreadListener.onResult(true);
                        } else {
                            onUIThreadListener.onResult(false);
                        }
                    } catch (Exception e
                            ) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(false);

                    }
                } else {
                    onUIThreadListener.onResult(false);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(false);
            }
        });
    }


    /**
     * 商家订单列表
     *
     * @param context
     * @param user_id
     * @param page
     * @param type
     * @param onUIThreadListener
     */
    public void getOrderListPresenter(BaseActivity context, String user_id, int page, int type, final OnUIThreadListener<ItemOrderResp> onUIThreadListener) {
        getModel().getOrderListModel(context, user_id, page, type, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家订单列表：" + jsonObject.toString());
                    try {
                        ItemOrderResp itemOrderList = JSON.parseObject(jsonObject.toString(), ItemOrderResp.class);
                        onUIThreadListener.onResult(itemOrderList);
                    } catch (Exception e) {
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

    /**
     * 取消订单
     *
     * @param context
     * @param user_id
     * @param order_id
     * @param onUIThreadListener
     */
    public void getCancelOrderSellerPresenter(BaseActivity context, String user_id, String order_id, final OnUIThreadListener<Boolean> onUIThreadListener) {
        getModel().getCancelOrderSellerModel(context, user_id, order_id, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家取消订单：" + jsonObject.toString());

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {
                            onUIThreadListener.onResult(true);
                        }
                    } catch (Exception e
                            ) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(false);
                    }
                } else {
                    onUIThreadListener.onResult(false);

                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(false);
            }
        });
    }

    /**
     * 获取商家金额信息
     *
     * @param context
     * @param user_id
     * @param onUIThreadListener
     */
    public void getServiceMoneyInfoPresenter(BaseActivity context, String user_id, final OnUIThreadListener<ItemServiceMoneyInfo> onUIThreadListener) {
        getModel().getServiceMoneyInfoModel(context, user_id, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家金额信息：" + jsonObject.toString());

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {

                            ItemServiceMoneyInfo data = JSON.parseObject(jsonObject.getString("data"), ItemServiceMoneyInfo.class);
                            onUIThreadListener.onResult(data);
                        }
                    } catch (Exception e
                            ) {
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

    /**
     * 商家我的服务
     *
     * @param activity
     * @param store_id
     * @param onUIThreadListener
     */
    public void getServiceMinePresenter(BaseActivity activity, String store_id, int page, final OnUIThreadListener<ItemCategoryMain> onUIThreadListener) {
        getModel().getServiceMineModel(activity, store_id, page, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家我的服务：" + jsonObject.toString());

                    try {

                        ItemCategoryMain itemCategoryMain = JSON.parseObject(jsonObject.toString(), ItemCategoryMain.class);
                        onUIThreadListener.onResult(itemCategoryMain);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("商家我的服务: " + e.toString());
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


    /**
     * 商家我的服务
     *
     * @param activity
     * @param user_id
     * @param onUIThreadListener
     */
    public void getServiceInMoneyDetailPresenter(BaseActivity activity, String user_id, int page, final OnUIThreadListener<ItemServiceInMoneyDetail> onUIThreadListener) {
        getModel().getServiceInMoneyDetailModel(activity, user_id, page, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家收入明细：" + jsonObject.toString());

                    try {

                        ItemServiceInMoneyDetail itemServiceInMoneyDetail = JSON.parseObject(jsonObject.toString(), ItemServiceInMoneyDetail.class);
                        onUIThreadListener.onResult(itemServiceInMoneyDetail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(null);
                        AppLog.Log("商家收入明细: " + e.toString());
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
 /**
     * 商家回复
     *
     * @param onUIThreadListener
     */
 public void getSellerReplyPresenter(BaseActivity context, String order_id,String reply, final OnUIThreadListener<Boolean> onUIThreadListener) {
        getModel().getSellerReplyModel(context, order_id, reply, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (jsonObject != null) {
                    AppLog.Log("商家回复评价提交：" + jsonObject.toString());

                    try {
                        int code = jsonObject.getIntValue("code");
                        if (Constant.SUCCESS_CODE == code) {
                            onUIThreadListener.onResult(true);
                        }
                    } catch (Exception e
                            ) {
                        e.printStackTrace();
                        onUIThreadListener.onResult(false);
                    }
                } else {
                    onUIThreadListener.onResult(false);

                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                onUIThreadListener.onResult(false);
            }
        });
    }


}
