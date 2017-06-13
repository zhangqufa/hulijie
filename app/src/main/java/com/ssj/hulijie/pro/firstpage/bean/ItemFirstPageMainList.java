package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by vic_zhang .
 * on 2017/3/30
 */

public class ItemFirstPageMainList {
    private String id;
    private String name;
    private String pic;
    private String txt;
    private String price;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "ItemFirstPageMainList{" +
                "price='" + price + '\'' +
                ", txt='" + txt + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
