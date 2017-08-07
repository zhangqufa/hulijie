package com.ssj.hulijie.pro.firstpage.bean;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/8/7
 */

public class DetailServiceEvaluate {
    private int count;

    private List<EvaluateItem> rows;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EvaluateItem> getRows() {
        return rows;
    }

    public void setRows(List<EvaluateItem> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DetailServiceEvaluate{" +
                "count=" + count +
                ", rows=" + rows +
                '}';
    }
}
