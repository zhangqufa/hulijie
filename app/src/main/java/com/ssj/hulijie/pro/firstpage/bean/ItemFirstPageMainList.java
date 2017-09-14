package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.ImageUrlFormat;

/**
 * Created by Administrator on 2017/9/10.
 */

public class ItemFirstPageMainList implements Parcelable {
    private String goods_id;
    private String goods_name;
    private String default_image;
    private String txt;
    private String price;

    public ItemFirstPageMainList() {
    }

    protected ItemFirstPageMainList(Parcel in) {
        goods_id = in.readString();
        goods_name = in.readString();
        default_image = in.readString();
        txt = in.readString();
        price = in.readString();
    }

    public static final Creator<ItemFirstPageMainList> CREATOR = new Creator<ItemFirstPageMainList>() {
        @Override
        public ItemFirstPageMainList createFromParcel(Parcel in) {
            return new ItemFirstPageMainList(in);
        }

        @Override
        public ItemFirstPageMainList[] newArray(int size) {
            return new ItemFirstPageMainList[size];
        }
    };

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }


    public String getDefault_image() {
        return ImageUrlFormat.urlFormat(default_image);
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    @Override
    public String toString() {
        return "ItemFirstPageMainList{" +
                "price='" + price + '\'' +
                ", txt='" + txt + '\'' +
                ", default_image='" + default_image + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_id='" + goods_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goods_id);
        parcel.writeString(goods_name);
        parcel.writeString(default_image);
        parcel.writeString(txt);
        parcel.writeString(price);
    }
}
