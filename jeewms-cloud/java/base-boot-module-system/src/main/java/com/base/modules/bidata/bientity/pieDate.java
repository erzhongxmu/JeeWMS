package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class
pieDate {


    @SerializedName("data")
    public List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * value : 9772.37
         * name : 直接访问
         */

        @SerializedName("value")
        private String value;
        @SerializedName("name")
        private String name;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
