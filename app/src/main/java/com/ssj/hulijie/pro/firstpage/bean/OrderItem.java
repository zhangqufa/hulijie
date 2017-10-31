package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class OrderItem  implements Parcelable{
    private String order_goods_id;
    private String order_amount;
    private String order_address;
    private String order_time;
    private String order_user_name;
    private String order_phone;
    private String order_mark;
    private String order_goods_name;
    private String order_goods_price;


    public OrderItem() {
    }

    protected OrderItem(Parcel in) {
        order_goods_id = in.readString();
        order_amount = in.readString();
        order_address = in.readString();
        order_time = in.readString();
        order_user_name = in.readString();
        order_phone = in.readString();
        order_mark = in.readString();
        order_goods_name = in.readString();
        order_goods_price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_goods_id);
        dest.writeString(order_amount);
        dest.writeString(order_address);
        dest.writeString(order_time);
        dest.writeString(order_user_name);
        dest.writeString(order_phone);
        dest.writeString(order_mark);
        dest.writeString(order_goods_name);
        dest.writeString(order_goods_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public String getOrder_goods_price() {
        return order_goods_price;
    }

    public void setOrder_goods_price(String order_goods_price) {
        this.order_goods_price = order_goods_price;
    }

    public String getOrder_goods_id() {
        return order_goods_id;
    }

    public void setOrder_goods_id(String order_goods_id) {
        this.order_goods_id = order_goods_id;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_user_name() {
        return order_user_name;
    }

    public void setOrder_user_name(String order_user_name) {
        this.order_user_name = order_user_name;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public String getOrder_mark() {
        return order_mark;
    }

    public void setOrder_mark(String order_mark) {
        this.order_mark = order_mark;
    }

    public String getOrder_goods_name() {
        return order_goods_name;
    }

    public void setOrder_goods_name(String order_goods_name) {
        this.order_goods_name = order_goods_name;
    }
}
