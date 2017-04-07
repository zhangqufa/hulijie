package com.ssj.hulijie.pro.db.model;

/**
 * Created by vic on 2016/12/2.
 */

public class FootMark {
    private String id;
    private String itemimg;
    private String itemtype;
    private String title;
    private String buyprice;
    private String couponbills;   //优惠券面值:category为2时此参数生效
    private int category;   //商品品种:1.超级返,2.优惠券
    private long addtime;

    public FootMark() {
    }

    public FootMark(String id, String itemimg, String itemtype, String title, String buyprice, String couponbills, int category, long addtime) {
        this.id = id;
        this.itemimg = itemimg;
        this.itemtype = itemtype;
        this.title = title;
        this.buyprice = buyprice;
        this.couponbills = couponbills;
        this.category = category;
        this.addtime = addtime;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemimg() {
        return itemimg;
    }

    public void setItemimg(String itemimg) {
        this.itemimg = itemimg;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice;
    }

    public String getCouponbills() {
        return couponbills;
    }

    public void setCouponbills(String couponbills) {
        this.couponbills = couponbills;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

}
