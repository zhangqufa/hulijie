package com.ssj.hulijie.pro.mine.bean;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CityGridItem {
    private String city;
    private String province;

    public CityGridItem() {
    }

    public CityGridItem(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
