package com.ssj.hulijie.pro.msg.bean;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MsgData {
    private String img;
    private String title;
    private String sub_title;
    private long time;

    public MsgData(String img, String title, String sub_title, long time) {
        this.img = img;
        this.title = title;
        this.sub_title = sub_title;
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
