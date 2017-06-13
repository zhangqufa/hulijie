package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by Administrator on 2017/5/14.
 */

public class FourpartData {
    private String name;
    private String txt;
    private String pic;
    private String id;

    public FourpartData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FourpartData{" +
                "name='" + name + '\'' +
                ", txt='" + txt + '\'' +
                ", pic='" + pic + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
