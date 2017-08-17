package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

import static android.R.attr.description;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailServiceItem implements Parcelable {

    private String goods_id;        //商品id
    private String goods_name;      //商品名称
    private long add_time;          //添加时间
    private String cate_id;         //商品分类
    private String cate_name;       //商品分类名称
    private String default_image;   //图片
    private List<String> img;     //描述图片
    private long last_update;       //最后更新时间
    private String price;           //服务价格



    /**
     * 9:05预约10:00，9:06预约10:30
     * 9:35预约10:30，9:36预约11:00
     */
    private long system_time;       //当前时间
    private int interval ;         //间隔时间
    private int ahead;              //提前时间

    private String start_time;  //开始营业时间（24小时制，如果是9点40分，就返回9.4）
    private String end_time;    //开始营业时间（24小时制，如果是9点40分，就返回9.4）

    public DetailServiceItem() {
    }

    protected DetailServiceItem(Parcel in) {
        goods_id = in.readString();
        goods_name = in.readString();
        add_time = in.readLong();
        cate_id = in.readString();
        cate_name = in.readString();
        default_image = in.readString();
        img = in.createStringArrayList();
        last_update = in.readLong();
        price = in.readString();
        system_time = in.readLong();
        interval = in.readInt();
        ahead = in.readInt();
        start_time = in.readString();
        end_time = in.readString();
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

    public long getSystem_time() {
        return system_time;
    }

    public void setSystem_time(long system_time) {
        this.system_time = system_time;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getAhead() {
        return ahead;
    }

    public void setAhead(int ahead) {
        this.ahead = ahead;
    }

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

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "DetailServiceItem{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", add_time=" + add_time +
                ", cate_id='" + cate_id + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", default_image='" + default_image + '\'' +
                ", img=" + img +
                ", last_update=" + last_update +
                ", price='" + price + '\'' +
                ", system_time=" + system_time +
                ", interval=" + interval +
                ", ahead=" + ahead +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goods_id);
        parcel.writeString(goods_name);
        parcel.writeLong(add_time);
        parcel.writeString(cate_id);
        parcel.writeString(cate_name);
        parcel.writeString(default_image);
        parcel.writeStringList(img);
        parcel.writeLong(last_update);
        parcel.writeString(price);
        parcel.writeLong(system_time);
        parcel.writeInt(interval);
        parcel.writeInt(ahead);
        parcel.writeString(start_time);
        parcel.writeString(end_time);

    }
}
