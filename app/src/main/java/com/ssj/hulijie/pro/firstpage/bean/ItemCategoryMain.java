package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ItemCategoryMain {

    /**
     * msg : 操作成功
     * data : {"rows":[{"add_time":"1487790289","goods_name":"擦玻璃","price":"170.00","goods_id":"3233","cate_id":"1984","cate_name":"保洁\t擦玻璃","default_image":"/data/files/store_431/goods_124/small_201702241022047720.jpg","last_update":"1487874127"},{"add_time":"1487788601","goods_name":"油烟机清洗","price":"150.00","goods_id":"3232","cate_id":"1983","cate_name":"保洁\t油烟机洗","default_image":"/data/files/store_431/goods_190/small_201702231036305273.jpg","last_update":"1487788601"},{"add_time":"1487788560","goods_name":"洗衣机清洗","price":"190.00","goods_id":"3231","cate_id":"1982","cate_name":"保洁\t洗衣机洗","default_image":"/data/files/store_431/goods_140/small_201702241022203689.jpg","last_update":"1487874142"},{"add_time":"1487788526","goods_name":"空调清洗","price":"170.00","goods_id":"3230","cate_id":"1981","cate_name":"保洁\t空调清洗","default_image":"/data/files/store_431/goods_94/small_201702231034546855.jpg","last_update":"1487788526"},{"add_time":"1487788466","goods_name":"冰箱清洗","price":"170.00","goods_id":"3229","cate_id":"1980","cate_name":"保洁\t冰箱清洗","default_image":"/data/files/store_431/goods_155/small_201702241022354697.jpg","last_update":"1487874157"}],"count":"5"}
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
         * rows : [{"add_time":"1487790289","goods_name":"擦玻璃","price":"170.00","goods_id":"3233","cate_id":"1984","cate_name":"保洁\t擦玻璃","default_image":"/data/files/store_431/goods_124/small_201702241022047720.jpg","last_update":"1487874127"},{"add_time":"1487788601","goods_name":"油烟机清洗","price":"150.00","goods_id":"3232","cate_id":"1983","cate_name":"保洁\t油烟机洗","default_image":"/data/files/store_431/goods_190/small_201702231036305273.jpg","last_update":"1487788601"},{"add_time":"1487788560","goods_name":"洗衣机清洗","price":"190.00","goods_id":"3231","cate_id":"1982","cate_name":"保洁\t洗衣机洗","default_image":"/data/files/store_431/goods_140/small_201702241022203689.jpg","last_update":"1487874142"},{"add_time":"1487788526","goods_name":"空调清洗","price":"170.00","goods_id":"3230","cate_id":"1981","cate_name":"保洁\t空调清洗","default_image":"/data/files/store_431/goods_94/small_201702231034546855.jpg","last_update":"1487788526"},{"add_time":"1487788466","goods_name":"冰箱清洗","price":"170.00","goods_id":"3229","cate_id":"1980","cate_name":"保洁\t冰箱清洗","default_image":"/data/files/store_431/goods_155/small_201702241022354697.jpg","last_update":"1487874157"}]
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
             * add_time : 1487790289
             * goods_name : 擦玻璃
             * price : 170.00
             * goods_id : 3233
             * cate_id : 1984
             * cate_name : 保洁	擦玻璃
             * default_image : /data/files/store_431/goods_124/small_201702241022047720.jpg
             * last_update : 1487874127
             */

            private String add_time;
            private String goods_name;
            private String price;
            private String goods_id;
            private String cate_id;
            private String cate_name;
            private String default_image;
            private String last_update;
            private String txt;

            public RowsBean() {
            }

            protected RowsBean(Parcel in) {
                add_time = in.readString();
                goods_name = in.readString();
                price = in.readString();
                goods_id = in.readString();
                cate_id = in.readString();
                cate_name = in.readString();
                default_image = in.readString();
                last_update = in.readString();
                txt = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(add_time);
                dest.writeString(goods_name);
                dest.writeString(price);
                dest.writeString(goods_id);
                dest.writeString(cate_id);
                dest.writeString(cate_name);
                dest.writeString(default_image);
                dest.writeString(last_update);
                dest.writeString(txt);
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

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

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

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }

            public String getDefault_image() {
                return ImageUrlFormat.urlFormat(default_image);
            }

            public void setDefault_image(String default_image) {
                this.default_image = default_image;
            }

            public String getLast_update() {
                return last_update;
            }

            public void setLast_update(String last_update) {
                this.last_update = last_update;
            }
        }
    }
}
