package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.ssj.hulijie.R;
import com.ssj.hulijie.alipay.Constants;
import com.ssj.hulijie.alipay.OrderInfoUtil2_0;
import com.ssj.hulijie.alipay.PayResult;
import com.ssj.hulijie.alipay.SignUtils;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by vic_zhang .
 * on 2017/8/4
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {
    private DetailServiceItem detail;
    private ImageView wechat_select, alipay_select;
    private PayStatus currentPayStatus = PayStatus.WECHAT;
    private Button btn_pay;

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
                    payForAlipay();
                } else if(currentPayStatus == PayStatus.WECHAT){
                    payForWechat();
                }

                break;
        }
    }

    private void payForWechat() {
    }

    private void payForAlipay() {
        alipay(detail.getGoods_name(),detail.getGoods_name(),"0.01");
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
    private void alipay(String subject, String body, String price) {
        String content = getOrderInfo(subject, body, price);
        AppLog.Log("content: " + content);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constants.PARTNER,content, true);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String sign = OrderInfoUtil2_0.getSign(params, Constants.RSA2_PRIVATE, true);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     *
     * @param subject
     *            订单名称
     * @param body
     *            订单描述
     * @param price
     *            订单价格
     * @return
     */
    private String getOrderInfo(String subject, String body, String price) {
        Map<String, String> map = new HashMap<>();
        map.put("timeout_express", "30m");
        map.put("product_code", "QUICK_MSECURITY_PAY");
        map.put("total_amount", price);
        map.put("subject", subject);
        map.put("body", body);
        map.put("out_trade_no", OrderInfoUtil2_0.getOutTradeNo());
        return JSON.toJSONString(map);
    }


    enum PayStatus{
        WECHAT,ALIPAY
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detail = getIntent().getParcelableExtra("detail");
        setContentView(R.layout.act_pay);
        initToolbar();
        initView();

    }

    private void initView() {
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
}
