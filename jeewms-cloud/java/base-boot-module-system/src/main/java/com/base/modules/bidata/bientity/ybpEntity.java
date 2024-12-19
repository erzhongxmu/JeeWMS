package com.base.modules.bidata.bientity;
//仪表盘
import com.google.gson.annotations.SerializedName;
public class ybpEntity {

    @SerializedName("data")
    public DBean data;
    public DBean getData() {
        return data;
    }

    public void setData(DBean data) {
        this.data = data;
    }

    public static class DBean {
        @SerializedName("min")
        private int min;
        @SerializedName("max")
        private int max;
        @SerializedName("label")//完成率
        private String label;
        @SerializedName("value")
        private int value;
        @SerializedName("unit")//百分比
        private String unit;

        public void setMin(int min) {
            this.min = min;
        }

        public int getMin() {
            return min;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMax() {
            return max;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnit() {
            return unit;
        }
    }
}