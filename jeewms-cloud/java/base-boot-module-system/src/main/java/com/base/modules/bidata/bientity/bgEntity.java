package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//表格、超期物料，对于存入料柜时间太长的物料，进行显示
public class bgEntity {
    @SerializedName("data")
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
    public static class DataBean {
        @SerializedName("type1")
        private String type1;
        @SerializedName("type2")
        private String type2;
        @SerializedName("type3")
        private String type3;
        @SerializedName("type4")
        private String type4;
        @SerializedName("type5")
        private String type5;

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public String getType1() {
            return type1;
        }

        public void setType2(String type2) {
            this.type2 = type2;
        }

        public String getType2() {
            return type2;
        }

        public String getType3() {
            return type3;
        }

        public void setType3(String type3) {
            this.type3 = type3;
        }

        public String getType4() {
            return type4;
        }

        public void setType4(String type4) {
            this.type4 = type4;
        }

        public String getType5() {
            return type5;
        }

        public void setType5(String type5) {
            this.type5 = type5;
        }
    }

}
