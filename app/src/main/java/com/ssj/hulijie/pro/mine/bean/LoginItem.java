package com.ssj.hulijie.pro.mine.bean;

/**
 * Created by Administrator on 2017/7/27.
 */

public class LoginItem {

    private String user_id;
    private String key;
    private String mobile;
    private String user_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "LoginItem{" +
                "user_id='" + user_id + '\'' +
                ", key='" + key + '\'' +
                ", mobile='" + mobile + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
