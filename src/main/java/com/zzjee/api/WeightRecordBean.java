package com.zzjee.api;

import java.io.Serializable;

public class WeightRecordBean implements Serializable {

    /**
     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
     */
    private static final long serialVersionUID = 1L;
    // id
    private String itemId;
    // 1.入库 2.越库
    private String blindStatus;
    // 扣皮重量
    private String bukleWeight;
    // 前商品id
    private String oldGoodsId;
    private String oldGoodsName;
    // 后商品名
    private String newGoodsId;
    private String newGoodsName;
    // 商品单位
    private String goodsUnit;
    // 仓库id
    private String storeId;
    // 用户id
    private String userId;
    // 机构id、名称
    private String orgId;
    private String orgName;
    // 1.入库 2.退货 3.出库 4.盘点
    private String flag;

    private String modifyGoodsPic;

    public String getModifyGoodsPic() {
        return modifyGoodsPic;
    }

    public void setModifyGoodsPic(String modifyGoodsPic) {
        this.modifyGoodsPic = modifyGoodsPic;
    }
    
    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBlindStatus() {
        return blindStatus;
    }

    public void setBlindStatus(String blindStatus) {
        this.blindStatus = blindStatus;
    }

    public String getBukleWeight() {
        return bukleWeight;
    }

    public void setBukleWeight(String bukleWeight) {
        this.bukleWeight = bukleWeight;
    }

    public String getOldGoodsId() {
        return oldGoodsId;
    }

    public void setOldGoodsId(String oldGoodsId) {
        this.oldGoodsId = oldGoodsId;
    }

    public String getOldGoodsName() {
        return oldGoodsName;
    }

    public void setOldGoodsName(String oldGoodsName) {
        this.oldGoodsName = oldGoodsName;
    }

    public String getNewGoodsId() {
        return newGoodsId;
    }

    public void setNewGoodsId(String newGoodsId) {
        this.newGoodsId = newGoodsId;
    }

    public String getNewGoodsName() {
        return newGoodsName;
    }

    public void setNewGoodsName(String newGoodsName) {
        this.newGoodsName = newGoodsName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
