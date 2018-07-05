package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ssj.hulijie.R;
import com.ssj.hulijie.alipay.PayResult;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.OrderItem;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.pro.mine.presenter.OrderListPresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;
import com.ssj.hulijie.wxapi.ConstantsWechat;
import com.ssj.hulijie.wxapi.ItemWechatPayResopse;
import com.ssj.hulijie.wxapi.WxUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by vic_zhang .
 * on 2017/8/4
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {
    private OrderItem orderItem;
    private ImageView wechat_select, alipay_select;
    private PayStatus currentPayStatus = PayStatus.WECHAT;
    private Button btn_pay;
    private TextView moeny;
    private OrderListPresenter presenter;

    private ItemOrderResp.DataBean.RowsBean data;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wechat_select:
                currentPayStatus = PayStatus.WECHAT;
                wechat_select.setImageResource(R.mipmap.address_sel_seleted);
                alipay_select.setImageResource(R.mipmap.address_sel_unseleted);
                break;
            case R.id.alipay_select:
                currentPayStatus = PayStatus.ALIPAY;
                wechat_select.setImageResource(R.mipmap.address_sel_unseleted);
                alipay_select.setImageResource(R.mipmap.address_sel_seleted);
                break;
            case R.id.btn_pay:
                if (currentPayStatus == PayStatus.ALIPAY) {
                    if (data != null) {
                        payForAlipayByOrderId();
                    } else {
                        payForAlipay();
                    }
                } else if (currentPayStatus == PayStatus.WECHAT) {
                    if (data != null) {
                        payForWechatByOrderId();
                    } else {
                        payForWechat();
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * 通过订单号用微信支付
     */
    private void payForWechatByOrderId() {
        if (data != null) {
            presenter.getWechatSignPresenter(this, data.getOrder_id(), new BasePresenter.OnUIThreadListener<ItemWechatPayResopse>() {
                @Override
                public void onResult(ItemWechatPayResopse result) {
                    if (result != null) {
                        callWeChatPay(result);
                    }
                }
            });
        }
    }

    /**
     * 通过订单号用支付宝支付
     */
    private void payForAlipayByOrderId() {
        if (data == null) {
            return;
        }
        presenter.getOrderSingPresenter(this, data.getOrder_id(), SharedUtil.getPreferStr(SharedKey.USER_ID), new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    callApliy(result);
                }

            }
        });
    }


    private void finishClose() {
        AppLog.Log("payact");
        setResult(RESULT_OK);
        finish();
    }

    private IWXAPI api;

    private void payForWechat() {
        SharedUtil.setPreferBool(SharedKey.PAY_SUCCESS, false);
        String user_id = SharedUtil.getPreferStr(SharedKey.USER_ID);
        String goods_id = orderItem.getOrder_goods_id();
        String amount = orderItem.getOrder_amount();
        String mobile = orderItem.getOrder_phone();
        String service_address = orderItem.getOrder_address_id();
        String buyer_name = orderItem.getOrder_user_name();
        long service_time = orderItem.getOrder_time();
        String remark = orderItem.getOrder_mark();
        presenter.getOrderForWechatPresenter(this, user_id, goods_id, amount, mobile, service_address, buyer_name, service_time, remark, new BasePresenter.OnUIThreadListener<ItemWechatPayResopse>() {
            @Override
            public void onResult(ItemWechatPayResopse result) {
                if (result != null) {
                    callWeChatPay(result);
                }
            }
        });
    }

    private void callWeChatPay(ItemWechatPayResopse item) {

        if (item == null) {
            return;
        }

        api = WXAPIFactory.createWXAPI(this, ConstantsWechat.APPID);
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.appId = item.getAppid();

        req.partnerId = item.getMch_id();
        req.prepayId = item.getPrepay_id();
        req.nonceStr = item.getNonce_str();
        req.timeStamp = "" + System.currentTimeMillis() / 1000;
        req.packageValue = "Sign=WXPay";

        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", req.appId);
        params.put("partnerid", req.partnerId);
        params.put("prepayid", req.prepayId);
        params.put("noncestr", req.nonceStr);
        params.put("timestamp", req.timeStamp);
        params.put("package", req.packageValue);
        req.sign = WxUtil.getSign(params);
        api.sendReq(req);
    }


    private void payForAlipay() {
        alipay();

    }

    private static final int SDK_PAY_FLAG = 100;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finishClose();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

                default:
                    break;
            }
        }
    };

    // 支付宝支付
    // payInfo:支付满足要求的订单信息
    private void alipay() {
        String user_id = SharedUtil.getPreferStr(SharedKey.USER_ID);
        String goods_id = orderItem.getOrder_goods_id();
        String amount = orderItem.getOrder_amount();
        String mobile = orderItem.getOrder_phone();
        String service_address = orderItem.getOrder_address_id();
        String buyer_name = orderItem.getOrder_user_name();
        long service_time = orderItem.getOrder_time();
        String remark = orderItem.getOrder_mark();
        presenter.getOrderSignTwoPresenter(this, user_id, goods_id, amount, mobile, service_address, buyer_name, service_time, remark, new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    AppLog.Log("orderInfo:" + result);
                    callApliy(result);
                }
            }
        });

    }
//    final String orderInfo = "alipay_sdk=alipay-sdk-php-20161101&app_id=2017010704910421&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%221505466180%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fjassj.com%2Fi%2Findex.php%2Forder%2Fcall_back&sign_type=RSA2&timestamp=2017-09-15+17%3A03%3A00&version=1.0&sign=Dioe%2BfzGnGc8LXk9sOpO%2BiLcT0kyaJVp7CfmUKLn1JaCyLK%2FjYxf1zu07EbROxcVA1P0fHnXMu6SYqg0zKoh4CYXeDxyjfsybXSd6GYhoYVnZMqC%2BglswjIolxlOQ5H7DU2AkcNqs5TRzbOlmSUPqHqj4BZ643lwxDjDrbAFs0Wx5orNNnN3GAkOlTJxovyR0a75VZRASpHwA0ZRbQcuSOvoKZogYEdFp7Em%2F6KSKwosXMWzuFvOnwGEPBZPqrBgGnkq3rSnenrqH04ZOjzPwsGGg3Z%2BHl7pVtm8H5g1O0akICTqKule4Ido1nHy%2B0mmxpuevu%2ByhL0ON3mB7Zw3HA%3D%3D";

    private void callApliy(String orderInfo) {
        if (orderInfo.contains("amp;")) {
            orderInfo = orderInfo.replace("amp;", "");

        }
        final String finalyStr = orderInfo;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(finalyStr, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    enum PayStatus {
        WECHAT, ALIPAY
    }


    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new OrderListPresenter(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderItem = getIntent().getParcelableExtra("orderItem");
        setContentView(R.layout.act_pay);
        data = getIntent().getParcelableExtra("data");
        initToolbar();
        initView();

    }

    private void initView() {
        moeny = (TextView) findViewById(R.id.moeny);
        if (data != null) {
            moeny.setText(data.getOrder_amount());
        }
        if (orderItem != null) {
            moeny.setText((Float.valueOf(orderItem.getOrder_goods_price()) * Integer.valueOf(orderItem.getOrder_amount())) + "");
        }
        btn_pay = (Button) findViewById(R.id.btn_pay);
        wechat_select = (ImageView) findViewById(R.id.wechat_select);
        alipay_select = (ImageView) findViewById(R.id.alipay_select);
        btn_pay.setOnClickListener(this);
        alipay_select.setOnClickListener(this);
        wechat_select.setOnClickListener(this);
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "支付收银台", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.Log("查询后台信息");
        boolean pay_result = SharedUtil.getPreferBool(SharedKey.PAY_SUCCESS, false);
        if (pay_result) {
            SharedUtil.setPreferBool(SharedKey.PAY_SUCCESS, false);
            finishClose();
        }

    }
}
