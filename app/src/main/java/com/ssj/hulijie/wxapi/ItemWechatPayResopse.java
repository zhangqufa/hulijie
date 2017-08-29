package com.ssj.hulijie.wxapi;

/**
 * Created by vic_zhang .
 * on 2017/8/29
 */

public class ItemWechatPayResopse {

    /**
     * <?xml version="1.0" encoding="utf-8"?>

     <xml>
     <return_code><![CDATA[SUCCESS]]></return_code>
     <return_msg><![CDATA[OK]]></return_msg>
     <appid><![CDATA[wx8f3516ce28406855]]></appid>
     <mch_id><![CDATA[1487143322]]></mch_id>
     <device_info><![CDATA[7bfb8ca7-a817-48ec-a994-a1e2642236cc]]></device_info>
     <nonce_str><![CDATA[Xo17iiKCL4IiRsZj]]></nonce_str>
     <sign><![CDATA[B9958AA4B1CDA739B2175EC55AC4903E]]></sign>
     <result_code><![CDATA[SUCCESS]]></result_code>
     <prepay_id><![CDATA[wx20170829110245067977b6fa0081474845]]></prepay_id>
     <trade_typetrade_type><![CDATA[APP]]></trade_type>
     </xml>
     */

    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String prepay_id;
    private String trade_type;

    @Override
    public String toString() {
        return "ItemWechatPayResopse{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", trade_typetrade_type='" + trade_type + '\'' +
                '}';
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
}
