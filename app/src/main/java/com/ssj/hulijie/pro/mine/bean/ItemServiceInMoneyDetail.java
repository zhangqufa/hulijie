package com.ssj.hulijie.pro.mine.bean;

import java.util.List;

/**
 * @author vic_zhang .
 *         on 2018/5/21
 */

public class ItemServiceInMoneyDetail {

    /**
     * data : {"count":"4","rows":[{"id":"4","order_id":"527","user_id":"431","amount":"0.01","is_add":"1","add_time":"1526030020","mark":"服务收入"}]}
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
         * count : 4
         * rows : [{"id":"4","order_id":"527","user_id":"431","amount":"0.01","is_add":"1","add_time":"1526030020","mark":"服务收入"}]
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
             * id : 4
             * order_id : 527
             * user_id : 431
             * amount : 0.01
             * is_add : 1
             * add_time : 1526030020
             * mark : 服务收入
             */

            private String id;
            private String order_id;
            private String user_id;
            private String amount;
            private int is_add;
            private long add_time;
            private String mark;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public int getIs_add() {
                return is_add;
            }

            public void setIs_add(int is_add) {
                this.is_add = is_add;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }
        }
    }
}
