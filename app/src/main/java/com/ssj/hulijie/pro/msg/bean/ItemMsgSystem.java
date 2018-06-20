package com.ssj.hulijie.pro.msg.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ItemMsgSystem {


    /**
     * data : [{"content":"抱歉，您的开店申请已被拒绝，原因如下： 333","add_time":"1400590382"},{"content":"抱歉，您的开店申请已被拒绝，原因如下： 不同意","add_time":"1400843881"},{"content":"恭喜，您的店铺已开通，赶快来用户中心发布商品吧。","add_time":"1400918316"},{"content":"您的店铺已被删除","add_time":"1400918373"},{"content":"恭喜，您的店铺已开通，赶快来用户中心发布商品吧。","add_time":"1400919295"},{"content":"您的店铺已被删除","add_time":"1400919412"},{"content":"抱歉，您的开店申请已被拒绝，原因如下： 123","add_time":"1400922689"},{"content":"抱歉，您的开店申请已被拒绝，原因如下： 123456","add_time":"1400922883"},{"content":"抱歉，您的开店申请已被拒绝，原因如下： 333","add_time":"1401708460"},{"content":"抱歉，您的开店申请已被拒绝，原因如下： 1234","add_time":"1401793532"}]
     * code : 10000
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 抱歉，您的开店申请已被拒绝，原因如下： 333
         * add_time : 1400590382
         */

        private String content;
        private String add_time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
