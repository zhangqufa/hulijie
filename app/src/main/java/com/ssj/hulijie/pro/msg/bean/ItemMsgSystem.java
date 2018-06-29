package com.ssj.hulijie.pro.msg.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ItemMsgSystem {


    /**
     * count : 1
     * rows : [{"add_time":"1506727007","content":"ddd"}]
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
         * add_time : 1506727007
         * content : ddd
         */

        private long add_time;
        private String content;

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
