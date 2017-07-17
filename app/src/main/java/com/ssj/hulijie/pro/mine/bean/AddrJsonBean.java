package com.ssj.hulijie.pro.mine.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */

public class AddrJsonBean  implements Serializable{

    private List<ProvincesBean> provinces;

    public List<ProvincesBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvincesBean> provinces) {
        this.provinces = provinces;
    }

    @Override
    public String toString() {
        return "AddrJsonBean{" +
                "provinces=" + provinces +
                '}';
    }

    public static class ProvincesBean implements Serializable {
        /**
         * name : 北京市
         * citys : ["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云区","延庆区","其他"]
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

        @Override
        public String toString() {
            return "ProvincesBean{" +
                    "name='" + name + '\'' +
                    ", citys=" + citys +
                    '}';
        }
    }
}
