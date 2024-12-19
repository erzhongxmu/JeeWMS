package com.base.modules.bidata.bientity;
//环形图
import com.google.gson.annotations.SerializedName;

public class labelEntity {


    /**
     * data : {"label":"人数增长","value":3,"data":51}
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
         * label : 人数增长
         * value : 3
         * data : 51
         */

        @SerializedName("label")
        private String label;
        @SerializedName("value")
        private String value;
        @SerializedName("data")
        private String data;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
