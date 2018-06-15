package com.ssj.hulijie.pro.firstpage.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class ItemEvaluate {

    /**
     * data : {"count":"1","rows":[{"evaluation":"5","comment":"哈哈哈哈啊哈，测试数据","subscore1":"5","subscore2":"5","subscore3":"5","images":"../uploads/20170617//5944fc3d0a99b.jpg","commentdate":"1497664447"}]}
     * code : 10000
     * msg : 操作成功
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * count : 1
         * rows : [{"evaluation":"5","comment":"哈哈哈哈啊哈，测试数据","subscore1":"5","subscore2":"5","subscore3":"5","images":"../uploads/20170617//5944fc3d0a99b.jpg","commentdate":"1497664447"}]
         */

        private int count;
        private List<RowsBean> rows;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * evaluation : 5
             * comment : 哈哈哈哈啊哈，测试数据
             * subscore1 : 5
             * subscore2 : 5
             * subscore3 : 5
             * images : ../uploads/20170617//5944fc3d0a99b.jpg
             * commentdate : 1497664447
             */

            private float evaluation;
            private String comment;
            private String subscore1;
            private String subscore2;
            private String subscore3;
            private String images;
            private long commentdate;
            private String buyer_name;

            public String getBuyer_name() {
                return buyer_name;
            }

            public void setBuyer_name(String buyer_name) {
                this.buyer_name = buyer_name;
            }

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

            public String getSubscore1() {
                return subscore1;
            }

            public void setSubscore1(String subscore1) {
                this.subscore1 = subscore1;
            }

            public String getSubscore2() {
                return subscore2;
            }

            public void setSubscore2(String subscore2) {
                this.subscore2 = subscore2;
            }

            public String getSubscore3() {
                return subscore3;
            }

            public void setSubscore3(String subscore3) {
                this.subscore3 = subscore3;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public long getCommentdate() {
                return commentdate;
            }

            public void setCommentdate(long commentdate) {
                this.commentdate = commentdate;
            }
        }
    }
}
