package com.zzjee.tms.entity;

import java.util.List;

/**
 * 该类用于封装分页查询返回的订单数据集合
 * 在进行分页查询时，可以将查询结果存储在这个类的实例中
 */

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DdPage {

    /**
     * 存储分页查询返回的订单数据列表
     * 这个列表包含了所有在当前分页查询结果中的订单实体对象
     */
    private List<TmsYwDingdanEntity> demos;

    /**
     * 获取分页查询返回的订单数据列表
     *
     * @return 包含订单实体的列表
     */
    public List<TmsYwDingdanEntity> getDemos() {
        return demos;
    }

    /**
     * 设置分页查询返回的订单数据列表
     *
     * @param demos 包含订单实体的列表
     */
    public void setDemos(List<TmsYwDingdanEntity> demos) {
        this.demos = demos;
    }

}
