package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ServiceItem {

    private String name;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ServiceItem{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
