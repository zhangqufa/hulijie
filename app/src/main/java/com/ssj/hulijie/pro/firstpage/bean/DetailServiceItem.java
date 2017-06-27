package com.ssj.hulijie.pro.firstpage.bean;

import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailServiceItem {

    private String goods_id;        //商品id
    private String goods_name;      //商品名称
    private long add_time;          //添加时间
    private String cate_id;         //商品分类
    private String cate_name;       //商品分类名称
    private String default_image;   //图片
    private String description;     //描述
    private long last_update;       //最后更新时间
    private String price;           //服务价格


    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getDefault_image() {

        return ImageUrlFormat.urlFormat(default_image);
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    public String getDescription() {
        return ImageUrlFormat.urlFormat(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLast_update() {
        return last_update;
    }

    public void setLast_update(long last_update) {
        this.last_update = last_update;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DetailItem{" +
                "price='" + price + '\'' +
                ", last_update=" + last_update +
                ", description='" + description + '\'' +
                ", default_image='" + default_image + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", cate_id='" + cate_id + '\'' +
                ", add_time=" + add_time +
                ", goods_name='" + goods_name + '\'' +
                ", goods_id='" + goods_id + '\'' +
                '}';
    }


}