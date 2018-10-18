package com.ssj.hulijie.pro.db.model;

public class City {
    public String name;
    public String pinyin;

    public City(String name, String pinyin) {
        super();
        this.name = name;
        this.pinyin = pinyin;
    }

    public City() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
