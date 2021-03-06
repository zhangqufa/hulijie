package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AddressItem implements Parcelable{

    /**
     * addr_id : 3
     * region_name : 中国	浙江省	台州	路桥区	十里长街
     * address : 椒江
     * phone_mob : 18868813294
     */

    private String addr_id;
    private String region_name;
    private String address;
    private String phone_mob;
    private int default_addr;
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return "AddressItem{" +
                "addr_id='" + addr_id + '\'' +
                ", region_name='" + region_name + '\'' +
                ", address='" + address + '\'' +
                ", phone_mob='" + phone_mob + '\'' +
                ", default_addr=" + default_addr +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public AddressItem() {
    }

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_mob() {
        return phone_mob;
    }

    public void setPhone_mob(String phone_mob) {
        this.phone_mob = phone_mob;
    }

    public int getDefault_addr() {
        return default_addr;
    }

    public void setDefault_addr(int default_addr) {
        this.default_addr = default_addr;
    }

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

    protected AddressItem(Parcel in) {
        addr_id = in.readString();
        region_name = in.readString();
        address = in.readString();
        phone_mob = in.readString();
        default_addr = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addr_id);
        dest.writeString(region_name);
        dest.writeString(address);
        dest.writeString(phone_mob);
        dest.writeInt(default_addr);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressItem> CREATOR = new Creator<AddressItem>() {
        @Override
        public AddressItem createFromParcel(Parcel in) {
            return new AddressItem(in);
        }

        @Override
        public AddressItem[] newArray(int size) {
            return new AddressItem[size];
        }
    };
}
