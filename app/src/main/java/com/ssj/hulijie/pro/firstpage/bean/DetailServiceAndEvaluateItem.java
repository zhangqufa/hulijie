package com.ssj.hulijie.pro.firstpage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailServiceAndEvaluateItem {

    private DetailServiceItem info;
    private DetailServiceEvaluate evaluate;

    public DetailServiceItem getDetail() {
        return info;
    }

    public void setDetail(DetailServiceItem info) {
        this.info = info;
    }

    public DetailServiceItem getInfo() {
        return info;
    }

    public void setInfo(DetailServiceItem info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "DetailServiceAndEvaluateItem{" +
                "info=" + info +
                ", evaluate=" + evaluate +
                '}';
    }

    public DetailServiceEvaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(DetailServiceEvaluate evaluate) {
        this.evaluate = evaluate;
    }
}
