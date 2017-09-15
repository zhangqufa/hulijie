package com.ssj.hulijie.pro.firstpage.bean;

import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class FourpartData {

    /**
     * msg : 操作成功
     * data : {"rows":[{"goods_name":"卫浴洁具","price":"30.00","goods_id":"3239","txt":"卫浴洁具","default_image":"/data/files/store_431/goods_194/small_201702281129541375.jpg"},{"goods_name":"防水补漏","price":"30.00","goods_id":"3240","txt":"防水补漏","default_image":"/data/files/store_431/goods_58/small_201702281130585042.jpg"},{"goods_name":"打孔管道疏通","price":"30.00","goods_id":"3241","txt":"打孔管道疏通","default_image":"/data/files/store_431/goods_46/small_201702281404068129.jpg"},{"goods_name":"预约卖车","price":"0.00","goods_id":"3242","txt":"预约卖车","default_image":"/data/files/store_431/goods_2/small_201703031023228875.jpg"}],"count":"4"}
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
         * rows : [{"goods_name":"卫浴洁具","price":"30.00","goods_id":"3239","txt":"卫浴洁具","default_image":"/data/files/store_431/goods_194/small_201702281129541375.jpg"},{"goods_name":"防水补漏","price":"30.00","goods_id":"3240","txt":"防水补漏","default_image":"/data/files/store_431/goods_58/small_201702281130585042.jpg"},{"goods_name":"打孔管道疏通","price":"30.00","goods_id":"3241","txt":"打孔管道疏通","default_image":"/data/files/store_431/goods_46/small_201702281404068129.jpg"},{"goods_name":"预约卖车","price":"0.00","goods_id":"3242","txt":"预约卖车","default_image":"/data/files/store_431/goods_2/small_201703031023228875.jpg"}]
         * count : 4
         */

        private String count;
        private List<RowsBean> rows;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
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
             * goods_name : 卫浴洁具
             * price : 30.00
             * goods_id : 3239
             * txt : 卫浴洁具
             * default_image : /data/files/store_431/goods_194/small_201702281129541375.jpg
             */

            private String goods_name;
            private String price;
            private String goods_id;
            private String txt;
            private String default_image;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            public String getDefault_image() {
                return ImageUrlFormat.urlFormat(default_image);
            }

            public void setDefault_image(String default_image) {
                this.default_image = default_image;
            }
        }
    }
}
