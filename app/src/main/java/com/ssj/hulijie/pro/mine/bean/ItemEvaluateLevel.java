package com.ssj.hulijie.pro.mine.bean;

/**
 * Created by Administrator on 2018/6/12.
 */

public class ItemEvaluateLevel {

    private int imgRes;

    private int imgSelected;

    private String title;

    private int level;

    private boolean isSelected;


    public ItemEvaluateLevel() {
    }

    public ItemEvaluateLevel(int imgRes, int imgSelected,String title, int level) {
        this.imgRes = imgRes;
        this.title = title;
        this.level = level;
        this.imgSelected = imgSelected;
    }

    public int getImgRes() {


        return imgRes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getImgSelected() {
        return imgSelected;
    }

    public void setImgSelected(int imgSelected) {
        this.imgSelected = imgSelected;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
