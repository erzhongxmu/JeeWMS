package com.base.modules.bidata.bientity;
/**
 * 环形图
 * 空余储位，显示当前的四台料柜的空余储位，每一小时更新一次
 */
import com.google.gson.annotations.SerializedName;
public class hxtEntity {
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
        private String value;
        @SerializedName("data")
        private String data;

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}
