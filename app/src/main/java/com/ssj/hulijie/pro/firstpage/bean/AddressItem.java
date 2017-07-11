package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AddressItem {

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
    private boolean isEdit;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

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
                ", isEdit=" + isEdit +
                '}';
    }
}
