package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */

public class ItemRemmendList {


    /**
     * data : {"count":"47","rows":[{"goods_id":"3209","name":"《现代系列》回归自然 G3-j3","pic":"/data/files/store_431/goods_29/small_201702161520297298.jpg","price":"2580.00","txt":"《现代系列》回归自然 G3-j3"},{"goods_id":"3207","name":"《现代系列》家的味道","pic":"/data/files/store_431/goods_168/small_201702161456089958.jpg","price":"2580.00","txt":"《现代系列》家的味道"},{"goods_id":"3225","name":"《现代系列》G5-x2   家庭装修","pic":"/data/files/store_431/goods_155/small_201702221402364006.jpg","price":"3280.00","txt":"《现代系列》G5-x2   家庭装修"},{"goods_id":"3206","name":"洗手池下水管更换（含配件）","pic":"/data/files/store_431/goods_134/small_201702161452143338.jpg","price":"89.00","txt":"洗手池下水管更换（含配件）"},{"goods_id":"3232","name":"油烟机清洗","pic":"/data/files/store_431/goods_190/small_201702231036305273.jpg","price":"150.00","txt":"油烟机清洗"},{"goods_id":"3212","name":"《欧式》","pic":"/data/files/store_431/goods_105/small_201702161641451809.jpg","price":"3280.00","txt":"《欧式》"},{"goods_id":"3224","name":"开锁换锁 汽车开锁 换锁芯 保险柜开锁 指纹锁","pic":"/data/files/store_431/goods_54/small_201702211600543893.jpg","price":"30.00","txt":"开锁换锁 汽车开锁 换锁芯 保险柜开锁 指纹锁"},{"goods_id":"3242","name":"预约卖车","pic":"/data/files/store_431/goods_2/small_201703031023228875.jpg","price":"0.00","txt":"预约卖车"},{"goods_id":"3241","name":"打孔管道疏通","pic":"/data/files/store_431/goods_46/small_201702281404068129.jpg","price":"30.00","txt":"打孔管道疏通"},{"goods_id":"3240","name":"防水补漏","pic":"/data/files/store_431/goods_58/small_201702281130585042.jpg","price":"30.00","txt":"防水补漏"}]}
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
         * count : 47
         * rows : [{"goods_id":"3209","name":"《现代系列》回归自然 G3-j3","pic":"/data/files/store_431/goods_29/small_201702161520297298.jpg","price":"2580.00","txt":"《现代系列》回归自然 G3-j3"},{"goods_id":"3207","name":"《现代系列》家的味道","pic":"/data/files/store_431/goods_168/small_201702161456089958.jpg","price":"2580.00","txt":"《现代系列》家的味道"},{"goods_id":"3225","name":"《现代系列》G5-x2   家庭装修","pic":"/data/files/store_431/goods_155/small_201702221402364006.jpg","price":"3280.00","txt":"《现代系列》G5-x2   家庭装修"},{"goods_id":"3206","name":"洗手池下水管更换（含配件）","pic":"/data/files/store_431/goods_134/small_201702161452143338.jpg","price":"89.00","txt":"洗手池下水管更换（含配件）"},{"goods_id":"3232","name":"油烟机清洗","pic":"/data/files/store_431/goods_190/small_201702231036305273.jpg","price":"150.00","txt":"油烟机清洗"},{"goods_id":"3212","name":"《欧式》","pic":"/data/files/store_431/goods_105/small_201702161641451809.jpg","price":"3280.00","txt":"《欧式》"},{"goods_id":"3224","name":"开锁换锁 汽车开锁 换锁芯 保险柜开锁 指纹锁","pic":"/data/files/store_431/goods_54/small_201702211600543893.jpg","price":"30.00","txt":"开锁换锁 汽车开锁 换锁芯 保险柜开锁 指纹锁"},{"goods_id":"3242","name":"预约卖车","pic":"/data/files/store_431/goods_2/small_201703031023228875.jpg","price":"0.00","txt":"预约卖车"},{"goods_id":"3241","name":"打孔管道疏通","pic":"/data/files/store_431/goods_46/small_201702281404068129.jpg","price":"30.00","txt":"打孔管道疏通"},{"goods_id":"3240","name":"防水补漏","pic":"/data/files/store_431/goods_58/small_201702281130585042.jpg","price":"30.00","txt":"防水补漏"}]
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

        public static class RowsBean implements Parcelable{
            /**
             * goods_id : 3209
             * name : 《现代系列》回归自然 G3-j3
             * pic : /data/files/store_431/goods_29/small_201702161520297298.jpg
             * price : 2580.00
             * txt : 《现代系列》回归自然 G3-j3
             */

            private String goods_id;
            private String name;
            private String pic;
            private String price;
            private String txt;

            public RowsBean() {
            }

            protected RowsBean(Parcel in) {
                goods_id = in.readString();
                name = in.readString();
                pic = in.readString();
                price = in.readString();
                txt = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(goods_id);
                dest.writeString(name);
                dest.writeString(pic);
                dest.writeString(price);
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

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return  ImageUrlFormat.urlFormat(pic);
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }
}
