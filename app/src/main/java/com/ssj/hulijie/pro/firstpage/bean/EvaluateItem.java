package com.ssj.hulijie.pro.firstpage.bean;

/**
 * Created by Administrator on 2017/6/17.
 */

public class EvaluateItem {

    private float evaluation;        //总体评分
    private String comment;         //评论内容
    private String images;      //图片
    private float subscore1;    //评论品质
    private float subscore2;    //评论服务
    private float subscore3;  //评论环境
    private long commentdate;  //评论时间

    public float getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(float evaluation) {
        this.evaluation = evaluation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public float getSubscore1() {
        return subscore1;
    }

    public void setSubscore1(float subscore1) {
        this.subscore1 = subscore1;
    }

    public float getSubscore2() {
        return subscore2;
    }

    public void setSubscore2(float subscore2) {
        this.subscore2 = subscore2;
    }

    public float getSubscore3() {
        return subscore3;
    }

    public void setSubscore3(float subscore3) {
        this.subscore3 = subscore3;
    }

    public long getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(long commentdate) {
        this.commentdate = commentdate;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "evaluation=" + evaluation +
                ", comment='" + comment + '\'' +
                ", images='" + images + '\'' +
                ", subscore1=" + subscore1 +
                ", subscore2=" + subscore2 +
                ", subscore3=" + subscore3 +
                ", commentdate=" + commentdate +
                '}';
    }
}
