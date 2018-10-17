package com.ssj.hulijie.pro.firstpage.view.location;

import java.util.List;

/**
 * @author vic_zhang .
 * on 2018/10/17
 */
public class ItemAddress {

    private List<ProvincesBean> provinces;

    public List<ProvincesBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvincesBean> provinces) {
        this.provinces = provinces;
    }

    public static class ProvincesBean {
        /**
         * name : 北京
         * citys : ["北京"]
         */

        private String name;
        private List<String> citys;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getCitys() {
            return citys;
        }

        public void setCitys(List<String> citys) {
            this.citys = citys;
        }
    }
}
