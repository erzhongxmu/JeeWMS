package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;
/**
 * 折线图
 * 按周显示，图表中要包含周别，日期及星期几，抓取每日进出料数据生成，每天显示第一天的数据，显示七天，
 * 第二周开始，再重新记录第二周的数据，记录方式和第一周一样，系统保留两周的数据报表以用来对比
 * */
import java.util.List;
public class zxtEntity {
    @SerializedName("data")
    public Bean data;
    public Bean getData() {
        return data;
    }
    public void setData(Bean data) {
        this.data = data;
    }

    public static class Bean {
        @SerializedName("name")
        private List<String> categories;
        @SerializedName("series")
        private List<SeriesB> series;

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setSeries(List<SeriesB> series) {
            this.series = series;
        }

        public List<SeriesB> getSeries() {
            return series;
        }

        public static class SeriesB {
            @SerializedName("name")
            private String name;
            @SerializedName("data")
            private List<Long> data;

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setData(List<Long> data) {
                this.data=data;
            }

            public List<Long> getData() {
                return data;
            }
        }
    }

}
