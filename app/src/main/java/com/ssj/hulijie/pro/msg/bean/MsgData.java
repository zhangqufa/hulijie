package com.ssj.hulijie.pro.msg.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MsgData implements Parcelable {
    private String img;
    private String title;
    private String sub_title;
    private long time;

    public MsgData(String img, String title, String sub_title, long time) {
        this.img = img;
        this.title = title;
        this.sub_title = sub_title;
        this.time = time;
    }

    protected MsgData(Parcel in) {
        img = in.readString();
        title = in.readString();
        sub_title = in.readString();
        time = in.readLong();
    }

    public static final Creator<MsgData> CREATOR = new Creator<MsgData>() {
        @Override
        public MsgData createFromParcel(Parcel in) {
            return new MsgData(in);
        }

        @Override
        public MsgData[] newArray(int size) {
            return new MsgData[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img);
        parcel.writeString(title);
        parcel.writeString(sub_title);
        parcel.writeLong(time);
    }
}
