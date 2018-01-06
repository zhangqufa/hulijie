package com.ssj.hulijie.pro.mine.bean;

import com.ssj.hulijie.utils.ImageUrlFormat;
import com.ssj.hulijie.utils.PictureUtil;

import java.util.List;

/**
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ItemServiceOrderList {

    /**
     * data : {"count":"14","rows":[{"order_id":"464","order_sn":"1735367742","status":"20","add_time":"1513735070","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"2幢1003","service_time":"1545274800","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"},{"order_id":"460","order_sn":"1734857022","status":"20","add_time":"1513346854","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"请选择服务地址","service_time":"12","service_seller":"","buyer_name":"22","remark":"","mobile":"2147483647"},{"order_id":"452","order_sn":"1734845938","status":"20","add_time":"1513318805","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"2幢1003","service_time":"1544859000","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"},{"order_id":"448","order_sn":"1734638351","status":"20","add_time":"1513140710","goods_amount":"0.01","order_amount":"0.01","goods_id":"3298","goods_name":"大自然设计机构50元/平米","quantity":"1","default_image":"/data/files/store_431/goods_48/small_201711211517286186.jpg","service_address":"2幢1003","service_time":"1544680800","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"},{"order_id":"444","order_sn":"1734464603","status":"20","add_time":"1512961884","goods_amount":"0.01","order_amount":"0.01","goods_id":"3298","goods_name":"大自然设计机构50元/平米","quantity":"1","default_image":"/data/files/store_431/goods_48/small_201711211517286186.jpg","service_address":"飞跃科创园 32","service_time":"1544502600","service_seller":"","buyer_name":"张屈发","remark":"未付款","mobile":"2147483647"},{"order_id":"443","order_sn":"1734416640","status":"20","add_time":"1512961626","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"飞跃科创园 32","service_time":"1544502600","service_seller":"","buyer_name":"张屈发","remark":"jj","mobile":"2147483647"},{"order_id":"442","order_sn":"1734413505","status":"20","add_time":"1512961562","goods_amount":"0.01","order_amount":"0.01","goods_id":"3298","goods_name":"大自然设计机构50元/平米","quantity":"1","default_image":"/data/files/store_431/goods_48/small_201711211517286186.jpg","service_address":"飞跃科创园 32","service_time":"1544490000","service_seller":"","buyer_name":"张屈发","remark":"看看","mobile":"2147483647"},{"order_id":"441","order_sn":"1734459879","status":"20","add_time":"1512960990","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"飞跃科创园 32","service_time":"1544500800","service_seller":"","buyer_name":"张屈发","remark":"图","mobile":"2147483647"},{"order_id":"432","order_sn":"1733191106","status":"20","add_time":"1511830986","goods_amount":"0.01","order_amount":"0.01","goods_id":"3227","goods_name":"金诺装饰  家庭装修  精装套餐1788元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_55/small_201702221450551058.jpg","service_address":"2幢1003","service_time":"1511834400","service_seller":"","buyer_name":"杨卓华","remark":"我要现代风格的设计师","mobile":"2147483647"},{"order_id":"431","order_sn":"1732951295","status":"20","add_time":"1511708186","goods_amount":"0.01","order_amount":"0.01","goods_id":"3210","goods_name":"俪源装饰 家庭装修 整装套餐 1850元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_91/small_201702161531316669.jpg","service_address":"2幢1003","service_time":"1511744400","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"}]}
     * code : 10000
     * msg : 操作成功
     */

    private DataBean data;
    private int code;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * count : 14
         * rows : [{"order_id":"464","order_sn":"1735367742","status":"20","add_time":"1513735070","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"2幢1003","service_time":"1545274800","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"},{"order_id":"460","order_sn":"1734857022","status":"20","add_time":"1513346854","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"请选择服务地址","service_time":"12","service_seller":"","buyer_name":"22","remark":"","mobile":"2147483647"},{"order_id":"452","order_sn":"1734845938","status":"20","add_time":"1513318805","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"2幢1003","service_time":"1544859000","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"},{"order_id":"448","order_sn":"1734638351","status":"20","add_time":"1513140710","goods_amount":"0.01","order_amount":"0.01","goods_id":"3298","goods_name":"大自然设计机构50元/平米","quantity":"1","default_image":"/data/files/store_431/goods_48/small_201711211517286186.jpg","service_address":"2幢1003","service_time":"1544680800","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"},{"order_id":"444","order_sn":"1734464603","status":"20","add_time":"1512961884","goods_amount":"0.01","order_amount":"0.01","goods_id":"3298","goods_name":"大自然设计机构50元/平米","quantity":"1","default_image":"/data/files/store_431/goods_48/small_201711211517286186.jpg","service_address":"飞跃科创园 32","service_time":"1544502600","service_seller":"","buyer_name":"张屈发","remark":"未付款","mobile":"2147483647"},{"order_id":"443","order_sn":"1734416640","status":"20","add_time":"1512961626","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"飞跃科创园 32","service_time":"1544502600","service_seller":"","buyer_name":"张屈发","remark":"jj","mobile":"2147483647"},{"order_id":"442","order_sn":"1734413505","status":"20","add_time":"1512961562","goods_amount":"0.01","order_amount":"0.01","goods_id":"3298","goods_name":"大自然设计机构50元/平米","quantity":"1","default_image":"/data/files/store_431/goods_48/small_201711211517286186.jpg","service_address":"飞跃科创园 32","service_time":"1544490000","service_seller":"","buyer_name":"张屈发","remark":"看看","mobile":"2147483647"},{"order_id":"441","order_sn":"1734459879","status":"20","add_time":"1512960990","goods_amount":"0.01","order_amount":"0.01","goods_id":"3201","goods_name":"大视野装饰  家庭装修  精装套餐2288元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_197/small_201702161426375698.jpg","service_address":"飞跃科创园 32","service_time":"1544500800","service_seller":"","buyer_name":"张屈发","remark":"图","mobile":"2147483647"},{"order_id":"432","order_sn":"1733191106","status":"20","add_time":"1511830986","goods_amount":"0.01","order_amount":"0.01","goods_id":"3227","goods_name":"金诺装饰  家庭装修  精装套餐1788元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_55/small_201702221450551058.jpg","service_address":"2幢1003","service_time":"1511834400","service_seller":"","buyer_name":"杨卓华","remark":"我要现代风格的设计师","mobile":"2147483647"},{"order_id":"431","order_sn":"1732951295","status":"20","add_time":"1511708186","goods_amount":"0.01","order_amount":"0.01","goods_id":"3210","goods_name":"俪源装饰 家庭装修 整装套餐 1850元/每平米","quantity":"1","default_image":"/data/files/store_431/goods_91/small_201702161531316669.jpg","service_address":"2幢1003","service_time":"1511744400","service_seller":"","buyer_name":"杨卓华","remark":"","mobile":"2147483647"}]
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

        public static class RowsBean {
            /**
             * order_id : 464
             * order_sn : 1735367742
             * status : 20
             * add_time : 1513735070
             * goods_amount : 0.01
             * order_amount : 0.01
             * goods_id : 3201
             * goods_name : 大视野装饰  家庭装修  精装套餐2288元/每平米
             * quantity : 1
             * default_image : /data/files/store_431/goods_197/small_201702161426375698.jpg
             * service_address : 2幢1003
             * service_time : 1545274800
             * service_seller :
             * buyer_name : 杨卓华
             * remark :
             * mobile : 2147483647
             */

            private String order_id;
            private String order_sn;
            private int status;
            private long add_time;
            private String goods_amount;
            private String order_amount;
            private String goods_id;
            private String goods_name;
            private String quantity;
            private String default_image;
            private String service_address;
            private long service_time;
            private String service_seller;
            private String buyer_name;
            private String remark;
            private String mobile;
            private double latitude;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public String getGoods_amount() {
                return goods_amount;
            }

            public void setGoods_amount(String goods_amount) {
                this.goods_amount = goods_amount;
            }

            public String getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
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

            public String getService_seller() {
                return service_seller;
            }

            public void setService_seller(String service_seller) {
                this.service_seller = service_seller;
            }

            public String getBuyer_name() {
                return buyer_name;
            }

            public void setBuyer_name(String buyer_name) {
                this.buyer_name = buyer_name;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}
