package com.zzjee.api;
/**
 * @Title: Controller
 * @Description: 出货通知
 * @author erzhongxmu
 * @date 2017-08-15 23:18:59
 * @version V1.0
 *
 */
public class OrderGoodsBean extends TGoodsInfoBean {

    /**
     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
     */
    private static final long serialVersionUID = 1L;

    // 订单id
    private String orderId;

    // 入库单id
    private String storageId;

    // 盲验id
    private String purchaseBlindId;

    // 机构名称
    private String orgName;

    // 仓库ID
    private String storeId;

    // 仓库名称
    private String storeName;

    // 供应商id
    private String supplierId;

    // 供应商名称
    private String supplierName;

    // 订单数量
    private Double orderAmount;
    private Double orderWeight;
    // 扣皮
    private Double buckleSkin;
    // 是否批量
    private Integer batchType;
    // 1.入库 2.越库
    private Integer blindStatus;

    private Double goodsAmount;
    private Double goodsWeight;

    // 仓库数量
    private Double inventoryAmount;
    private Double inventoryWeight;

    // 验收人图片
    private String userPic;
    private String goodsPicItem;
    private String userVideo;
    private String goodsVideo;

    // 101501 采购单入库
    // 101502 无采购单入库
    // 101503 盲验入库
    private String StorageType;

    private String itemId;
    
    private Integer storageAmount;
    
    private Double  surplusAmount;
    private Double  surplusWeight;
    
    
    public Double getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public Double getSurplusWeight() {
		return surplusWeight;
	}

	public void setSurplusWeight(Double surplusWeight) {
		this.surplusWeight = surplusWeight;
	}

	public String getPurchaseBlindId() {
        return purchaseBlindId;
    }

    public void setPurchaseBlindId(String purchaseBlindId) {
        this.purchaseBlindId = purchaseBlindId;
    }

    public Integer getBlindStatus() {
        return blindStatus;
    }

    public void setBlindStatus(Integer blindStatus) {
        this.blindStatus = blindStatus;
    }

    public Integer getBatchType() {
        return batchType;
    }

    public void setBatchType(Integer batchType) {
        this.batchType = batchType;
    }

    public Double getBuckleSkin() {
        return buckleSkin;
    }

    public void setBuckleSkin(Double buckleSkin) {
        this.buckleSkin = buckleSkin;
    }

    public Double getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(Double orderWeight) {
        this.orderWeight = orderWeight;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Double getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Double inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public Double getInventoryWeight() {
        return inventoryWeight;
    }

    public void setInventoryWeight(Double inventoryWeight) {
        this.inventoryWeight = inventoryWeight;
    }

    public String getStorageType() {
        return StorageType;
    }

    public void setStorageType(String storageType) {
        StorageType = storageType;
    }

    public Double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getGoodsPicItem() {
        return goodsPicItem;
    }

    public void setGoodsPicItem(String goodsPicItem) {
        this.goodsPicItem = goodsPicItem;
    }

    public String getUserVideo() {
        return userVideo;
    }

    public void setUserVideo(String userVideo) {
        this.userVideo = userVideo;
    }

    public String getGoodsVideo() {
        return goodsVideo;
    }

    public void setGoodsVideo(String goodsVideo) {
        this.goodsVideo = goodsVideo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

	public Integer getStorageAmount() {
		return storageAmount;
	}

	public void setStorageAmount(Integer storageAmount) {
		this.storageAmount = storageAmount;
	}

}
