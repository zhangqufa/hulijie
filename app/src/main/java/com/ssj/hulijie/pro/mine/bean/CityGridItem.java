package com.ssj.hulijie.pro.mine.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CityGridItem implements Parcelable{
    private String city;
    private String province;

    public CityGridItem() {
    }

    public CityGridItem(String city, String province) {
        this.city = city;
        this.province = province;
    }

    protected CityGridItem(Parcel in) {
        city = in.readString();
        province = in.readString();
    }

    public static final Creator<CityGridItem> CREATOR = new Creator<CityGridItem>() {
        @Override
        public CityGridItem createFromParcel(Parcel in) {
            return new CityGridItem(in);
        }

        @Override
        public CityGridItem[] newArray(int size) {
            return new CityGridItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(province);
    }
}
