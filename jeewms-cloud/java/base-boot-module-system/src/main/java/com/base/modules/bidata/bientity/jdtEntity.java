package com.base.modules.bidata.bientity;
//进度条,当前任务，实时显示料柜的当前出料任务，实时更新
import com.google.gson.annotations.SerializedName;
public class jdtEntity {

    @SerializedName("data")
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("label")
        private String label;
        @SerializedName("value")
        private int value;
        @SerializedName("data")
        private int data;

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

        public void setData(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

    }
}
