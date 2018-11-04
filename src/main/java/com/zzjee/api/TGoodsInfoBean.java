package com.zzjee.api;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>表t_goods_info数据库Bean .</p>
 */
public class TGoodsInfoBean implements Serializable {
    /**
     * <p>商品id .</p>
     */
    private String goodsId;

    /**
     * <p>商品名称 .</p>
     */
    private String goodsName;

    /**
     * <p>商品图片 .</p>
     */
    private String goodsPic;

    /**
     * <p>商品编码 .</p>
     */
    private String goodsCode;

    /**
     * <p>检索码 .</p>
     */
    private String searchCode;

    /**
     * <p>结算单位 .</p>
     */
    private String goodsUnit;

    /**
     * <p>结算类型，1按重量，2按数量 .</p>
     */
    private Integer meterType;

    /**
     * <p>商品规格 .</p>
     */
    private String goodsSpec;

    /**
     * <p>商品换算率 .</p>
     */
    private Double goodsRate;

    /**
     * <p>商品单价 .</p>
     */
    private Double goodsPrice;

    /**
     * <p>商品类型，1重量。2数量 .</p>
     */
    private Integer settleType;

    /**
     * <p>商品单位 .</p>
     */
    private String settleUnit;

    /**
     * <p>保鲜天数 .</p>
     */
    private Integer freshDays;

    /**
     * <p>安全库存数 .</p>
     */
    private Integer safeInventoryNum;

    /**
     * <p>商品类别ID .</p>
     */
    private String classId;

    /**
     * <p>商品所属机构 .</p>
     */
    private String orgId;

    /**
     * <p>机构名称 .</p>
     */
    private String orgName;

    /**
     * <p>创建时间 .</p>
     */
    private Date createTime;

    /**
     * <p>创建人ID .</p>
     */
    private String createUserId;

    /**
     * <p> .</p>
     */
    private Date modifyTime;

    /**
     * <p> .</p>
     */
    private Date modifyTimeParam;

    /**
     * <p>修改人ID .</p>
     */
    private String modifyUserId;

    /**
     * <p>删除标志：1表示正常，2表示删除 .</p>
     */
    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public Integer getMeterType() {
        return meterType;
    }

    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Double getGoodsRate() {
        return goodsRate;
    }

    public void setGoodsRate(Double goodsRate) {
        this.goodsRate = goodsRate;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public String getSettleUnit() {
        return settleUnit;
    }

    public void setSettleUnit(String settleUnit) {
        this.settleUnit = settleUnit;
    }

    public Integer getFreshDays() {
        return freshDays;
    }

    public void setFreshDays(Integer freshDays) {
        this.freshDays = freshDays;
    }

    public Integer getSafeInventoryNum() {
        return safeInventoryNum;
    }

    public void setSafeInventoryNum(Integer safeInventoryNum) {
        this.safeInventoryNum = safeInventoryNum;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public Date getModifyTimeParam() {
        return modifyTimeParam;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setModifyTimeParam(Date modifyTimeParam) {
        this.modifyTimeParam = modifyTimeParam;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPic=").append(goodsPic);
        sb.append(", goodsCode=").append(goodsCode);
        sb.append(", searchCode=").append(searchCode);
        sb.append(", goodsUnit=").append(goodsUnit);
        sb.append(", meterType=").append(meterType);
        sb.append(", goodsSpec=").append(goodsSpec);
        sb.append(", goodsRate=").append(goodsRate);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", settleType=").append(settleType);
        sb.append(", settleUnit=").append(settleUnit);
        sb.append(", freshDays=").append(freshDays);
        sb.append(", safeInventoryNum=").append(safeInventoryNum);
        sb.append(", classId=").append(classId);
        sb.append(", orgId=").append(orgId);
        sb.append(", orgName=").append(orgName);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", modifyUserId=").append(modifyUserId);
        sb.append(", delFlag=").append(delFlag);
        sb.append("]");
        return sb.toString();
    }
}