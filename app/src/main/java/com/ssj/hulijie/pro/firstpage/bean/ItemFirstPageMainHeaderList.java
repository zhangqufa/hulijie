package com.ssj.hulijie.pro.firstpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.ImageUrlFormat;

/**
 * Created by vic_zhang .
 * on 2017/3/31
 */

public class ItemFirstPageMainHeaderList implements Parcelable {

    private String id;
    private String pic;
    private String name;


    protected ItemFirstPageMainHeaderList(Parcel in) {
        id = in.readString();
        pic = in.readString();
        name = in.readString();
    }

    public static final Creator<ItemFirstPageMainHeaderList> CREATOR = new Creator<ItemFirstPageMainHeaderList>() {
        @Override
        public ItemFirstPageMainHeaderList createFromParcel(Parcel in) {
            return new ItemFirstPageMainHeaderList(in);
        }

        @Override
        public ItemFirstPageMainHeaderList[] newArray(int size) {
            return new ItemFirstPageMainHeaderList[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return ImageUrlFormat.urlFormat(pic);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(pic);
        parcel.writeString(name);
    }
}
