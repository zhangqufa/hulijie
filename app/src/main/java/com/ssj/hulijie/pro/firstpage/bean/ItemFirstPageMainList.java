package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.ImageUrlFormat;

/**
 * Created by vic_zhang .
 * on 2017/3/30
 */

public class ItemFirstPageMainList implements Parcelable {
    private String id;
    private String name;
    private String pic;
    private String txt;
    private String price;

    public ItemFirstPageMainList() {
    }

    protected ItemFirstPageMainList(Parcel in) {
        id = in.readString();
        name = in.readString();
        pic = in.readString();
        txt = in.readString();
        price = in.readString();
    }

    public static final Creator<ItemFirstPageMainList> CREATOR = new Creator<ItemFirstPageMainList>() {
        @Override
        public ItemFirstPageMainList createFromParcel(Parcel in) {
            return new ItemFirstPageMainList(in);
        }

        @Override
        public ItemFirstPageMainList[] newArray(int size) {
            return new ItemFirstPageMainList[size];
        }
    };

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
        return ImageUrlFormat.urlFormat(pic);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(pic);
        parcel.writeString(txt);
        parcel.writeString(price);
    }
}
