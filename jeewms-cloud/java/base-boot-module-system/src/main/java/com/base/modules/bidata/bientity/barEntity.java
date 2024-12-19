package com.base.modules.bidata.bientity;
//柱状图
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class barEntity {

    /**
     * data : {"categories":["潍坊","安丘","杭州","深圳","杭州","深圳","广州","连云港"],"series":[{"name":"手机品牌","data":["9346.66","3840.90","2950.97","547.96","5348.68","8505.43","2505.25","431.30"]},{"name":"其他手机品牌","data":["6422.90","4426.66","5599.32","4687.69","1243.39","5539.96","2956.75","1520.88"]}]}
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
        @SerializedName("categories")
        private List<String> categories;
        @SerializedName("series")
        private List<SeriesBean> series;

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public List<SeriesBean> getSeries() {
            return series;
        }

        public void setSeries(List<SeriesBean> series) {
            this.series = series;
        }

        public static class SeriesBean {
            /**
             * name : 手机品牌
             * data : ["9346.66","3840.90","2950.97","547.96","5348.68","8505.43","2505.25","431.30"]
             */

            @SerializedName("name")
            private String name;
            @SerializedName("data")
            private List<String> data;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getData() {
                return data;
            }

            public void setData(List<String> data) {
                this.data = data;
            }
        }
    }
}
