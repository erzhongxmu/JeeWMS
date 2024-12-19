package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;

public class perEntity {

    /**
     * data : {"max":100,"min":0,"name":"完成率","unit":"%","value":21,"hideName":false}
     */

    @SerializedName("data")
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * max : 100
         * min : 0
         * name : 完成率
         * unit : %
         * value : 21
         * hideName : false
         */

        @SerializedName("max")
        private int max;
        @SerializedName("min")
        private int min;
        @SerializedName("name")
        private String name;
        @SerializedName("unit")
        private String unit;
        @SerializedName("value")
        private int value;
        @SerializedName("hideName")
        private boolean hideName;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public boolean isHideName() {
            return hideName;
        }

        public void setHideName(boolean hideName) {
            this.hideName = hideName;
        }
    }
}
