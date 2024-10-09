package com.zzjee.ba.vo;

import java.util.List;

/**
 * @Package com.zzjee.ba.vo
 * @date 2021/8/20 11:38 上午
 * @description
 */
public class BaGoodsCategoryVoo {

    private String categoryName;

    private String id;

    private String pid;

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public BaGoodsCategoryVoo setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
        return this;
    }

    private int categoryLevel;

    private List<BaGoodsCategoryVoo> baGoodsCategory;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<BaGoodsCategoryVoo> getBaGoodsCategory() {
        return baGoodsCategory;
    }

    public void setBaGoodsCategory(List<BaGoodsCategoryVoo> baGoodsCategory) {
        this.baGoodsCategory = baGoodsCategory;
    }
}
