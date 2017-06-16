package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by vic_zhang .
 * on 2017/3/31
 */

public class ItemFirstPageMainHeaderList {

    private String id;
    private String pic;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ItemFirstPageMainHeaderList(String img, String title) {
        this.pic = img;
        this.name = title;
    }

    public ItemFirstPageMainHeaderList() {
    }

    @Override
    public String toString() {
        return "ItemFirstPageMainHeaderList{" +
                "id='" + id + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
