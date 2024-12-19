package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class listEntity {

    @SerializedName("data")
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 物品2
         * sex : 0.82
         * count : 35.13
         */

        @SerializedName("name")
        private String name;
        @SerializedName("value1")
        private String value1;
        @SerializedName("value2")
        private String value2;
        @SerializedName("value3")
        private String value3;
        @SerializedName("value4")
        private String value4;
        @SerializedName("value5")
        private String value5;
        @SerializedName("value6")
        private String value6;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getValue3() {
            return value3;
        }

        public void setValue3(String value3) {
            this.value3 = value3;
        }

        public String getValue4() {
            return value4;
        }

        public void setValue4(String value4) {
            this.value4 = value4;
        }

        public String getValue5() {
            return value5;
        }

        public void setValue5(String value5) {
            this.value5 = value5;
        }

        public String getValue6() {
            return value6;
        }

        public void setValue6(String value6) {
            this.value6 = value6;
        }
    }
}
