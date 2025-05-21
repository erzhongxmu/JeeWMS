package com.zzjee.ba.vo;

import java.util.List;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class BaGoodsCategoryVo {

    private String label;

    private Integer value;

    public String getLabel() {
        return label;
    }

    public BaGoodsCategoryVo setLabel(String label) {
        this.label = label;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public BaGoodsCategoryVo setValue(Integer value) {
        this.value = value;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public BaGoodsCategoryVo setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public List<BaGoodsCategoryVo> getChildren() {
        return children;
    }

    public BaGoodsCategoryVo setChildren(List<BaGoodsCategoryVo> children) {
        this.children = children;
        return this;
    }

    private Integer pid;

    private List<BaGoodsCategoryVo> children;
}
