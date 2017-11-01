package com.ssj.hulijie.pro.mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class ItemOrderResp {

    /**
     * msg : 操作成功
     * data : {"rows":[{"buyer_name":"张屈发","mobile":"2147483647","remark":"涂","goods_id":"3162","order_id":"351","goods_amount":"0.01","quantity":"1","order_sn":"1730339361","add_time":"1509412647","goods_name":"支付宝测试《空气》 无色无味","status":"11","service_seller":"","order_amount":"0.01","default_image":"/data/files/store_431/goods_36/small_201702101257166976.jpg","service_address":"民利小区 280","service_time":"2147483647"},{"buyer_name":"张屈发","mobile":"2147483647","remark":"请准时","goods_id":"3162","order_id":"350","goods_amount":"0.01","quantity":"1","order_sn":"1730214904","add_time":"1509375772","goods_name":"支付宝测试《空气》 无色无味","status":"11","service_seller":"","order_amount":"0.01","default_image":"/data/files/store_431/goods_36/small_201702101257166976.jpg","service_address":"民利小区 280","service_time":"1"},{"buyer_name":"","mobile":"0","remark":"","goods_id":"3226","order_id":"331","goods_amount":"3280.00","quantity":"1","order_sn":"1730058156","add_time":"1509194961","goods_name":"《欧式》 G5-J2  家庭装修","status":"11","service_seller":"","order_amount":"3280.00","default_image":"/data/files/store_431/goods_101/small_201702221431418555.jpg","service_address":"","service_time":"0"},{"buyer_name":"","mobile":"0","remark":"","goods_id":"3229","order_id":"320","goods_amount":"170.00","quantity":"1","order_sn":"1729896402","add_time":"1508981806","goods_name":"冰箱清洗","status":"11","service_seller":"","order_amount":"170.00","default_image":"/data/files/store_431/goods_155/small_201702241022354697.jpg","service_address":"","service_time":"0"},{"buyer_name":"","mobile":"0","remark":"","goods_id":"3242","order_id":"319","goods_amount":"0.00","quantity":"1","order_sn":"1729851949","add_time":"1508981794","goods_name":"预约卖车","status":"11","service_seller":"","order_amount":"0.00","default_image":"/data/files/store_431/goods_2/small_201703031023228875.jpg","service_address":"","service_time":"0"}],"count":"5"}
     * code : 10000
     */

    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * rows : [{"buyer_name":"张屈发","mobile":"2147483647","remark":"涂","goods_id":"3162","order_id":"351","goods_amount":"0.01","quantity":"1","order_sn":"1730339361","add_time":"1509412647","goods_name":"支付宝测试《空气》 无色无味","status":"11","service_seller":"","order_amount":"0.01","default_image":"/data/files/store_431/goods_36/small_201702101257166976.jpg","service_address":"民利小区 280","service_time":"2147483647"},{"buyer_name":"张屈发","mobile":"2147483647","remark":"请准时","goods_id":"3162","order_id":"350","goods_amount":"0.01","quantity":"1","order_sn":"1730214904","add_time":"1509375772","goods_name":"支付宝测试《空气》 无色无味","status":"11","service_seller":"","order_amount":"0.01","default_image":"/data/files/store_431/goods_36/small_201702101257166976.jpg","service_address":"民利小区 280","service_time":"1"},{"buyer_name":"","mobile":"0","remark":"","goods_id":"3226","order_id":"331","goods_amount":"3280.00","quantity":"1","order_sn":"1730058156","add_time":"1509194961","goods_name":"《欧式》 G5-J2  家庭装修","status":"11","service_seller":"","order_amount":"3280.00","default_image":"/data/files/store_431/goods_101/small_201702221431418555.jpg","service_address":"","service_time":"0"},{"buyer_name":"","mobile":"0","remark":"","goods_id":"3229","order_id":"320","goods_amount":"170.00","quantity":"1","order_sn":"1729896402","add_time":"1508981806","goods_name":"冰箱清洗","status":"11","service_seller":"","order_amount":"170.00","default_image":"/data/files/store_431/goods_155/small_201702241022354697.jpg","service_address":"","service_time":"0"},{"buyer_name":"","mobile":"0","remark":"","goods_id":"3242","order_id":"319","goods_amount":"0.00","quantity":"1","order_sn":"1729851949","add_time":"1508981794","goods_name":"预约卖车","status":"11","service_seller":"","order_amount":"0.00","default_image":"/data/files/store_431/goods_2/small_201703031023228875.jpg","service_address":"","service_time":"0"}]
         * count : 5
         */

        private int count;
        private List<RowsBean> rows;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean implements Parcelable {
            /**
             * buyer_name : 张屈发
             * mobile : 2147483647
             * remark : 涂
             * goods_id : 3162
             * order_id : 351
             * goods_amount : 0.01
             * quantity : 1
             * order_sn : 1730339361
             * add_time : 1509412647
             * goods_name : 支付宝测试《空气》 无色无味
             * status : 11
             * service_seller :
             * order_amount : 0.01
             * default_image : /data/files/store_431/goods_36/small_201702101257166976.jpg
             * service_address : 民利小区 280
             * service_time : 2147483647
             */

            private String buyer_name;
            private String mobile;
            private String remark;
            private String goods_id;
            private String order_id;
            private String goods_amount;
            private String quantity;
            private String order_sn;
            private long add_time;
            private String goods_name;
            private int status;
            private String service_seller;
            private String order_amount;
            private String default_image;
            private String service_address;
            private long service_time;

            public RowsBean() {
            }

            protected RowsBean(Parcel in) {
                buyer_name = in.readString();
                mobile = in.readString();
                remark = in.readString();
                goods_id = in.readString();
                order_id = in.readString();
                goods_amount = in.readString();
                quantity = in.readString();
                order_sn = in.readString();
                add_time = in.readLong();
                goods_name = in.readString();
                status = in.readInt();
                service_seller = in.readString();
                order_amount = in.readString();
                default_image = in.readString();
                service_address = in.readString();
                service_time = in.readLong();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(buyer_name);
                dest.writeString(mobile);
                dest.writeString(remark);
                dest.writeString(goods_id);
                dest.writeString(order_id);
                dest.writeString(goods_amount);
                dest.writeString(quantity);
                dest.writeString(order_sn);
                dest.writeLong(add_time);
                dest.writeString(goods_name);
                dest.writeInt(status);
                dest.writeString(service_seller);
                dest.writeString(order_amount);
                dest.writeString(default_image);
                dest.writeString(service_address);
                dest.writeLong(service_time);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
                @Override
                public RowsBean createFromParcel(Parcel in) {
                    return new RowsBean(in);
                }

                @Override
                public RowsBean[] newArray(int size) {
                    return new RowsBean[size];
                }
            };

            public String getBuyer_name() {
                return buyer_name;
            }

            public void setBuyer_name(String buyer_name) {
                this.buyer_name = buyer_name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getGoods_amount() {
                return goods_amount;
            }

            public void setGoods_amount(String goods_amount) {
                this.goods_amount = goods_amount;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getService_seller() {
                return service_seller;
            }

            public void setService_seller(String service_seller) {
                this.service_seller = service_seller;
            }

            public String getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            public String getDefault_image() {
                return ImageUrlFormat.urlFormat(default_image);
            }

            public void setDefault_image(String default_image) {
                this.default_image = default_image;
            }

            public String getService_address() {
                return service_address;
            }

            public void setService_address(String service_address) {
                this.service_address = service_address;
            }

            public long getService_time() {
                return service_time;
            }

            public void setService_time(long service_time) {
                this.service_time = service_time;
            }
        }
    }
}
