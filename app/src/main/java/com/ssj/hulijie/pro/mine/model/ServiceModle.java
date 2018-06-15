package com.ssj.hulijie.pro.mine.model;

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
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ServiceModle extends BaseModel {

    public ServiceModle(Context context) {
        super(context);
    }

    public void getAcceptOrderListModel(BaseActivity content, int page, int pagesize, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_ORDER_SERVICE_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("page", page);
        request.add("pagesize", pagesize);
        CallServer.getRequestInstance().add(content, 0, request, httpListener, true, false);

    }

    public void serviceGetOrderModel(BaseActivity content, String order_id, String user_id, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_ORDER_SERVICE_GETORDER;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("order_id", order_id);
        request.add("user_id", user_id);
        CallServer.getRequestInstance().add(content, 0, request, httpListener, true, true);
    }

    /**
     * 商家订单列表
     *
     * @param context
     * @param user_id
     * @param page
     * @param type
     * @param httpListener
     */
    public void getOrderListModel(BaseActivity context, String user_id, int page, int type, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_ORDER_SERVICE_ORDER_LIST;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("page", page);
        request.add("type", type);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }


    /**
     * 取消订单
     *
     * @param context
     * @param user_id
     * @param order_id
     * @param httpListener
     */
    public void getCancelOrderSellerModel(BaseActivity context, String user_id, String order_id, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_ORDER_CANCEL_SELLER;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("order_id", order_id);
        request.add("user_id", user_id);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }

    public void getServiceMoneyInfoModel(BaseActivity context, String user_id, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_SERVER_MONEY_INFO;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }


    /**
     * 商家我的服务
     *
     * @param context
     * @param store_id
     * @param httpListener
     */
    public void getServiceMineModel(BaseActivity context, String store_id, int page, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_SERVER_MINE;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("store_id", store_id);
        request.add("page", page);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);
    }

    /**
     * 收入明细
     *
     * @param context
     * @param user_id
     * @param page
     * @param httpListener
     */
    public void getServiceInMoneyDetailModel(BaseActivity context, String user_id, int page, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_SERVER_IN_MONEY_DETAIL;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("page", page);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);

    }

    /**
     * 商家回复
     *
     * @param context
     * @param httpListener
     */
    public void getSellerReplyModel(BaseActivity context, String order_id,String reply, HttpListener<JSONObject> httpListener) {
        String url = AppURL.URL_EVALUATE_REPLY_SUBMIT;
        Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
        request.add("order_id", order_id);
        request.add("reply", reply);
        CallServer.getRequestInstance().add(context, 0, request, httpListener, true, true);

    }
}
