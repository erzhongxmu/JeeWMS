package com.base.modules.bidata.bientity;
//按天显示，每天早8点进行刷新，当前板块显示当天四台料柜的进出料信息，每十分钟或者半小时更新一次数据

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class zztEntity {
    @SerializedName("data")
    public DBean data;
    public DBean getData() {
        return data;
    }
    public void setData(DBean data) {
        this.data = data;
    }

    public static class DBean {
        @SerializedName("categories")
        private List<String> categories;
        @SerializedName("series")
        private List<SeriesBan> series;

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setSeries(List<SeriesBan> series) {
            this.series = series;
        }

        public List<SeriesBan> getSeries() {
            return series;
        }

        public static class SeriesBan {
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




