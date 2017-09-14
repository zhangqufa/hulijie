package com.ssj.hulijie.pro.firstpage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */

public class ItemCategory {

    /**
     * data : [{"name":"保洁","id":"1415","pic":"/data/files/mall/brand/1415.png"},{"name":"家电维修","id":"162","pic":"/data/files/mall/brand/162.png"},{"name":"数码维修","id":"2047","pic":"/data/files/mall/brand/2047.png"},{"name":"装修","id":"134","pic":"/data/files/mall/brand/134.png"},{"name":"房屋维修","id":"2051","pic":"/data/files/mall/brand/2051.png"},{"name":"保姆月嫂","id":"2046","pic":"/data/files/mall/brand/2046.png"},{"name":"开锁换锁","id":"2045","pic":"/data/files/mall/brand/2045.png"},{"name":"汽车服务","id":"2043","pic":"/data/files/mall/brand/2043.png"},{"name":"婚庆","id":"57","pic":"/data/files/mall/brand/57.png"},{"name":"搬家","id":"21","pic":"/data/files/mall/brand/21.png"},{"name":"工商财税","id":"2036","pic":"/data/files/mall/brand/2036.png"},{"name":"品牌设计","id":"2048","pic":"/data/files/mall/brand/2048.png"},{"name":"技术开发","id":"2049","pic":"/data/files/mall/brand/2049.png"},{"name":"上门安装","id":"2050","pic":"/data/files/mall/brand/2050.png"},{"name":"广告制作","id":"2052","pic":"/data/files/mall/brand/2052.png"},{"id":0,"name":"分类","pic":"/i/image/fenlei.png"}]
     * code : 10000
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 保洁
         * id : 1415
         * pic : /data/files/mall/brand/1415.png
         */

        private String name;
        private String id;
        private String pic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
