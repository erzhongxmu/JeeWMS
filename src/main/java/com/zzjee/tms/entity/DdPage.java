package com.zzjee.tms.entity;


import java.util.List;

/**
 * 该类用于封装分页查询返回的订单数据集合。
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
