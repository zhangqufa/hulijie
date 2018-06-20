package com.ssj.hulijie.pro.msg.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ItemMsgHuoDong {

    /**
     * count : 1
     * rows : [{"article_id":"26","code":"","title":"u72d0u72f8u8857APPu5f00u59cbu4e0bu8f7du4e86uff01uff01uff01","cate_id":"6","store_id":"0","link":"","content":"","sort_order":"255","if_show":"1","add_time":"1527815840"}]
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
         * article_id : 26
         * code :
         * title : u72d0u72f8u8857APPu5f00u59cbu4e0bu8f7du4e86uff01uff01uff01
         * cate_id : 6
         * store_id : 0
         * link :
         * content :
         * sort_order : 255
         * if_show : 1
         * add_time : 1527815840
         */

        private String article_id;
        private String code;
        private String title;
        private String cate_id;
        private String store_id;
        private String link;
        private String content;
        private String sort_order;
        private String if_show;
        private String add_time;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSort_order() {
            return sort_order;
        }

        public void setSort_order(String sort_order) {
            this.sort_order = sort_order;
        }

        public String getIf_show() {
            return if_show;
        }

        public void setIf_show(String if_show) {
            this.if_show = if_show;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
