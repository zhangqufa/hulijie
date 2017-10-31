package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.ssj.hulijie.R;
import com.ssj.hulijie.alipay.OrderInfoUtil2_0;
import com.ssj.hulijie.alipay.PayResult;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.nohttp.CallServer;
import com.ssj.hulijie.nohttp.HttpListener;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.OrderItem;
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
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;


/**
 * Created by vic_zhang .
 * on 2017/8/4
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {
    private OrderItem orderItem;
    private ImageView wechat_select, alipay_select;
    private PayStatus currentPayStatus = PayStatus.ALIPAY;
    private Button btn_pay;
    private TextView moeny;

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
                } else if (currentPayStatus == PayStatus.WECHAT) {
                    payForWechat();
                }

                break;
            default:break;
        }
    }


    private void finishClose() {
        AppLog.Log("payact");
        setResult(RESULT_OK);
        finish();
    }

    private IWXAPI api;



    private void payForWechat() {
        SharedUtil.setPreferBool(SharedKey.PAY_SUCCESS, false);
        String outTradeNo = OrderInfoUtil2_0.getOutTradeNo();
        String nonce_str = WxUtil.getRandomStringByLength(8);
        //1.统一下单 ：
        final String str_tongyi = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", ConstantsWechat.APPID);
        params.put("mch_id", ConstantsWechat.MCH_ID);
        params.put("device_info", UUID.randomUUID().toString()); //设备号
        params.put("nonce_str", nonce_str);
        params.put("body", orderItem.getOrder_goods_name());//商品描述
        params.put("out_trade_no", outTradeNo);
        params.put("total_fee", "1"); //单位分
        params.put("trade_type", "APP");
        params.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");
        final String xml_str = WxUtil.parseString2Xml(params, WxUtil.getSign(params));
        AppLog.Log("xml_str:" + xml_str);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    byte[] xmlbyte = xml_str.toString().getBytes("UTF-8");

                    System.out.println(xmlbyte);

                    URL url = new URL(str_tongyi);


                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setDoOutput(true);// 允许输出
                    conn.setDoInput(true);
                    conn.setUseCaches(false);// 不使用缓存
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("Content-Length",
                            String.valueOf(xmlbyte.length));
                    conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                    conn.setRequestProperty("X-ClientType", "2");//发送自定义的头信息

                    conn.getOutputStream().write(xmlbyte);
                    conn.getOutputStream().flush();
                    conn.getOutputStream().close();


                    if (conn.getResponseCode() != 200)
                        throw new RuntimeException("请求url失败");

                    InputStream is = conn.getInputStream();// 获取返回数据

                    // 使用输出流来输出字符(可选)
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = is.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }
                    String string = out.toString("UTF-8");
                    AppLog.Log(string);
                    out.close();


                    // xml解析
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(new ByteArrayInputStream(string.getBytes("UTF-8")), "UTF-8");
                    ItemWechatPayResopse item = new ItemWechatPayResopse();
                    int eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if ("return_code".equals(parser.getName())) {
                                String return_code = parser.nextText();
                                item.setReturn_code(return_code);
                            } else if ("return_msg".equals(parser.getName())) {
                                String return_msg = parser.nextText();
                                item.setReturn_msg(return_msg);
                            } else if ("appid".equals(parser.getName())) {
                                String appid = parser.nextText();
                                item.setAppid(appid);
                            }else if ("mch_id".equals(parser.getName())) {
                                String mch_id = parser.nextText();
                                item.setMch_id(mch_id);
                            }else if ("device_info".equals(parser.getName())) {
                                String device_info = parser.nextText();
                                item.setDevice_info(device_info);
                            }else if ("nonce_str".equals(parser.getName())) {
                                String nonce_str = parser.nextText();
                                item.setNonce_str(nonce_str);
                            }else if ("sign".equals(parser.getName())) {
                                String sign = parser.nextText();
                                item.setSign(sign);
                            }else if ("result_code".equals(parser.getName())) {
                                String result_code = parser.nextText();
                                item.setResult_code(result_code);
                            }else if ("prepay_id".equals(parser.getName())) {
                                String prepay_id = parser.nextText();
                                item.setPrepay_id(prepay_id);
                            }else if ("trade_type".equals(parser.getName())) {
                                String trade_type = parser.nextText();
                                item.setTrade_type(trade_type);
                            }

                        }
                        eventType = parser.next();
                    }
                    AppLog.Log("item: "+item.toString());
                    mHandler.obtainMessage(0, item).sendToTarget();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }).start();
    }
    private Handler mHandler   = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ItemWechatPayResopse itemWechatPayResopse  =(ItemWechatPayResopse) msg.obj;
            callWeChatPay(itemWechatPayResopse);
        }
    };
    private void callWeChatPay(ItemWechatPayResopse item) {
        if (item == null) {
            return;
        }
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(ConstantsWechat.APPID);
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.appId =item.getAppid();
        req.partnerId = item.getMch_id();
        req.prepayId = item.getPrepay_id();
        req.nonceStr = item.getNonce_str();
        req.timeStamp =""+System.currentTimeMillis()/1000;
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
//        String content = getOrderInfo(subject, body, price);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constants.PARTNER, content, true);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String sign = OrderInfoUtil2_0.getSign(params, Constants.RSA2_PRIVATE, true);
//        final String orderInfo = orderParam + "&" + sign;
        String url = "http://jassj.com/i/index.php/order/order";
        Request<String> req = NoHttp.createStringRequest(url, RequestMethod.POST);
        req.add("user_id",SharedUtil.getPreferStr(SharedKey.USER_ID));
        req.add("goods_id", orderItem.getOrder_goods_id());
        req.add("amount", orderItem.getOrder_amount());
        req.add("mobile", orderItem.getOrder_phone());
        req.add("service_address", orderItem.getOrder_address());
        req.add("buyer_name",orderItem.getOrder_user_name());
        req.add("service_time", orderItem.getOrder_time()*1000);
        req.add("remark", orderItem.getOrder_mark());
        CallServer.getRequestInstance().add(this, 0, req, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                if (!TextUtils.isEmpty(response.toString())) {

                    String s = response.get();
                    AppLog.Log("orderInfo:"+s);
                    callApliy(s);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, true);


    }
//    final String orderInfo = "alipay_sdk=alipay-sdk-php-20161101&app_id=2017010704910421&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%221505466180%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fjassj.com%2Fi%2Findex.php%2Forder%2Fcall_back&sign_type=RSA2&timestamp=2017-09-15+17%3A03%3A00&version=1.0&sign=Dioe%2BfzGnGc8LXk9sOpO%2BiLcT0kyaJVp7CfmUKLn1JaCyLK%2FjYxf1zu07EbROxcVA1P0fHnXMu6SYqg0zKoh4CYXeDxyjfsybXSd6GYhoYVnZMqC%2BglswjIolxlOQ5H7DU2AkcNqs5TRzbOlmSUPqHqj4BZ643lwxDjDrbAFs0Wx5orNNnN3GAkOlTJxovyR0a75VZRASpHwA0ZRbQcuSOvoKZogYEdFp7Em%2F6KSKwosXMWzuFvOnwGEPBZPqrBgGnkq3rSnenrqH04ZOjzPwsGGg3Z%2BHl7pVtm8H5g1O0akICTqKule4Ido1nHy%2B0mmxpuevu%2ByhL0ON3mB7Zw3HA%3D%3D";

    private void callApliy(String orderInfo){
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

    /**
     * @param subject 订单名称
     * @param body    订单描述
     * @param price   订单价格
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


    enum PayStatus {
        WECHAT, ALIPAY
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderItem = getIntent().getParcelableExtra("orderItem");
        setContentView(R.layout.act_pay);
        initToolbar();
        initView();

    }

    private void initView() {
        moeny = (TextView) findViewById(R.id.moeny);
        moeny.setText((Float.valueOf(orderItem.getOrder_goods_price())*Integer.valueOf(orderItem.getOrder_amount()))+"");
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
            SharedUtil.setPreferBool(SharedKey.PAY_SUCCESS,false);
            finishClose();
        }

    }
}
