package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by vic_zhang .
 * on 2017/3/31
 */

public class ItemFirstPageMainHeaderList {

    private String id;
    private String img;
    private String title;
    private int catetory;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getCatetory() {
        return catetory;
    }

    public void setCatetory(int catetory) {
        this.catetory = catetory;
    }

    public ItemFirstPageMainHeaderList(String img, String title) {
        this.img = img;
        this.title = title;
    }
}
