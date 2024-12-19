package com.base.modules.bi.model;


import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.entity.zdOnlCgreportParam;

import java.util.Arrays;
import java.util.List;

public class zdOnlCgreportModel {

    private zdOnlCgreportHead head;
    private List<zdOnlCgreportParam> params;
    private String deleteParamIds;
    private List<zdOnlCgreportItem> items;
    private String deleteItemIds;

    public zdOnlCgreportModel() {
    }

    public zdOnlCgreportHead getHead() {
        return this.head;
    }

    public void setHead(zdOnlCgreportHead head) {
        this.head = head;
    }

    public List<zdOnlCgreportParam> getParams() {
        return this.params;
    }

    public void setParams(List<zdOnlCgreportParam> params) {
        this.params = params;
    }

    public List<zdOnlCgreportItem> getItems() {
        return this.items;
    }

    public void setItems(List<zdOnlCgreportItem> items) {
        this.items = items;
    }

    public String getDeleteParamIds() {
        return this.deleteParamIds;
    }

    public List<String> getDeleteParamIdList() {
        return Arrays.asList(this.deleteParamIds.split(","));
    }

    public void setDeleteParamIds(String deleteParamIds) {
        this.deleteParamIds = deleteParamIds;
    }

    public String getDeleteItemIds() {
        return this.deleteItemIds;
    }

    public List<String> getDeleteItemIdList() {
        return Arrays.asList(this.deleteItemIds.split(","));
    }

    public void setDeleteItemIds(String deleteItemIds) {
        this.deleteItemIds = deleteItemIds;
    }
}
