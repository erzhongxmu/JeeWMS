package com.zzjee.api;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>表t_store_info数据库Bean .</p>
 */
public class TStoreInfoBean implements Serializable {
    /**
     * <p>仓库ID .</p>
     */
    private String storeId;

    /**
     * <p>仓库编号 .</p>
     */
    private String storeNo;

    /**
     * <p>仓库名称 .</p>
     */
    private String storeName;

    /**
     * <p>仓库地址 .</p>
     */
    private String storeAddr;

    /**
     * <p>仓库管理员 .</p>
     */
    private String storeAdmin;

    /**
     * <p>分类IDS .</p>
     */
    private String classIds;

    /**
     * <p> .</p>
     */
    private String classNames;

    /**
     * <p>机构ID .</p>
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public String getStoreAdmin() {
        return storeAdmin;
    }

    public void setStoreAdmin(String storeAdmin) {
        this.storeAdmin = storeAdmin;
    }

    public String getClassIds() {
        return classIds;
    }

    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassNames(String classNames) {
        this.classNames = classNames;
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
        sb.append(", storeId=").append(storeId);
        sb.append(", storeNo=").append(storeNo);
        sb.append(", storeName=").append(storeName);
        sb.append(", storeAddr=").append(storeAddr);
        sb.append(", storeAdmin=").append(storeAdmin);
        sb.append(", classIds=").append(classIds);
        sb.append(", classNames=").append(classNames);
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