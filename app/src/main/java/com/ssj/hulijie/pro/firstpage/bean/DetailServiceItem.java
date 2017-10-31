package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailServiceItem implements Parcelable {


    /**
     * end_time : 18
     * interval : 30
     * goods_id : 3229
     * start_time : 9
     * cate_name : 保洁	冰箱清洗
     * img : ["/ueditor/php/upload/image/20170223/1487817252556155.jpg"]
     * last_update : 1487874157
     * goods_name : 冰箱清洗
     * add_time : 1487788466
     * price : 170.00
     * ahead : 30
     * cate_id : 1980
     * default_image : /data/files/store_431/goods_155/small_201702241022354697.jpg
     * system_time : 1505482506
     */

    private String end_time;
    private int interval;
    private String goods_id;
    private String start_time;
    private String cate_name;
    private String last_update;
    private String goods_name;
    private String add_time;
    private String price;
    private int ahead;
    private String cate_id;
    private String default_image;
    private long system_time;
    private List<String> img;

    public DetailServiceItem() {
    }

    protected DetailServiceItem(Parcel in) {
        end_time = in.readString();
        interval = in.readInt();
        goods_id = in.readString();
        start_time = in.readString();
        cate_name = in.readString();
        last_update = in.readString();
        goods_name = in.readString();
        add_time = in.readString();
        price = in.readString();
        ahead = in.readInt();
        cate_id = in.readString();
        default_image = in.readString();
        system_time = in.readLong();
        img = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(end_time);
        dest.writeInt(interval);
        dest.writeString(goods_id);
        dest.writeString(start_time);
        dest.writeString(cate_name);
        dest.writeString(last_update);
        dest.writeString(goods_name);
        dest.writeString(add_time);
        dest.writeString(price);
        dest.writeInt(ahead);
        dest.writeString(cate_id);
        dest.writeString(default_image);
        dest.writeLong(system_time);
        dest.writeStringList(img);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetailServiceItem> CREATOR = new Creator<DetailServiceItem>() {
        @Override
        public DetailServiceItem createFromParcel(Parcel in) {
            return new DetailServiceItem(in);
        }

        @Override
        public DetailServiceItem[] newArray(int size) {
            return new DetailServiceItem[size];
        }
    };

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAhead() {
        return ahead;
    }

    public void setAhead(int ahead) {
        this.ahead = ahead;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getDefault_image() {
        return ImageUrlFormat.urlFormat(default_image);
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    public long getSystem_time() {
        return system_time;
    }

    public void setSystem_time(long system_time) {
        this.system_time = system_time;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
