package com.ssj.hulijie.pro.firstpage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailServiceAndEvaluateItem {

    private DetailServiceItem info;
    private List<EvaluateItem> evaluate;

    public DetailServiceItem getDetail() {
        return info;
    }

    public void setDetail(DetailServiceItem info) {
        this.info = info;
    }

    public List<EvaluateItem> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateItem> evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "DetailServiceAndEvaluateItem{" +
                "info=" + info +
                ", evaluate=" + evaluate +
                '}';
    }
}
