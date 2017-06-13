package com.ssj.hulijie.pro.firstpage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CatetoryItem {


    /**
     * cate_name : 货运搬家
     * parent_id : 0
     * cate_id : 21
     * child : [{"cate_name":"厢货","parent_id":"21","cate_id":"2016"},{"cate_name":"金杯","parent_id":"21","cate_id":"2017"}]
     */

    private String cate_name;
    private String parent_id;
    private String cate_id;
    private List<CatetoryChildItem> child;

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public List<CatetoryChildItem> getChild() {
        return child;
    }

    public void setChild(List<CatetoryChildItem> child) {
        this.child = child;
    }


    @Override
    public String toString() {
        return "CatetoryItem{" +
                "cate_name='" + cate_name + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", cate_id='" + cate_id + '\'' +
                ", child=" + child +
                '}';
    }

    public static class CatetoryChildItem {
        /**
         * cate_name : 厢货
         * parent_id : 21
         * cate_id : 2016
         */

        private String cate_name;
        private String parent_id;
        private String cate_id;

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        @Override
        public String toString() {
            return "CatetoryChildItem{" +
                    "cate_name='" + cate_name + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", cate_id='" + cate_id + '\'' +
                    '}';
        }
    }


}
