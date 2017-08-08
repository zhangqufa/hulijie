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
    private boolean isDefault;

    public AddressItem() {
    }

    protected AddressItem(Parcel in) {
        addr_id = in.readString();
        region_name = in.readString();
        address = in.readString();
        phone_mob = in.readString();
        isDefault = in.readByte() != 0;
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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

    @Override
    public String toString() {
        return "AddressItem{" +
                "addr_id='" + addr_id + '\'' +
                ", region_name='" + region_name + '\'' +
                ", address='" + address + '\'' +
                ", phone_mob='" + phone_mob + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(addr_id);
        parcel.writeString(region_name);
        parcel.writeString(address);
        parcel.writeString(phone_mob);
        parcel.writeByte((byte) (isDefault ? 1 : 0));
    }
}
