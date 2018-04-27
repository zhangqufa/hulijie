package com.ssj.hulijie.pro.mine.bean;

/**
 * @author vic_zhang .
 *         on 2018/4/20
 */

public class ItemImageUpload {
    /**
     * msg : 上传成功
     * status : 1
     * reurl : /upload/2017083120444696870.png|/upload/2017083120444696871.png|/upload/2017083120444696872.png
     */

    private String msg;
    private int status;
    private String reurl;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReurl() {
        return reurl;
    }

    public void setReurl(String reurl) {
        this.reurl = reurl;
    }
}
