package com.ssj.hulijie.pro.db.model;

/**
 * Created by vic on 2016/12/2.
 */

public class ItemSearchHistory {
    private int id;
    private String name;
    private long addtime;



    public ItemSearchHistory() {
    }

    public ItemSearchHistory(String name, long addtime) {
        this.name = name;
        this.addtime = addtime;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemSearchHistory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addtime=" + addtime +
                '}';
    }
}
