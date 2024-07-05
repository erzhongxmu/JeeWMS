package com.zzjee.tms.entity;


import java.util.List;

/**
 * 该类用于封装分页查询返回的订单数据集合。
 * 在进行分页查询时，可以将查询结果存储在这个类的实例中，便于后续的处理或展示。
 */
public class DdPage {
    private List<TmsYwDingdanEntity> demos;

    public List<TmsYwDingdanEntity> getDemos() {
        return demos;
    }

    public void setDemos(List<TmsYwDingdanEntity> demos) {
        this.demos = demos;
    }
}
