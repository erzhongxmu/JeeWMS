package com.zzjee.wmutil.dsc;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class goodsListRes {

    @JsonProperty("result")
    private String result;
    @JsonProperty("error")
    private int error;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("info")
    private InfoBean info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {

        @JsonProperty("filter")
        private FilterBean filter;
        @JsonProperty("page_count")
        private int pageCount;
        @JsonProperty("record_count")
        private String recordCount;
        @JsonProperty("list")
        private List<ListBean> list;

        public FilterBean getFilter() {
            return filter;
        }

        public void setFilter(FilterBean filter) {
            this.filter = filter;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public String getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(String recordCount) {
            this.recordCount = recordCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class FilterBean {
            /**
             * page : 1
             * page_size : 15
             * record_count : 287
             * page_count : 20
             */

            @JsonProperty("page")
            private int page;
            @JsonProperty("page_size")
            private int pageSize;
            @JsonProperty("record_count")
            private String recordCount;
            @JsonProperty("page_count")
            private int pageCount;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public String getRecordCount() {
                return recordCount;
            }

            public void setRecordCount(String recordCount) {
                this.recordCount = recordCount;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }
        }

        public static class ListBean {

            @JsonProperty("goods_id")
            private String goodsId;
            @JsonProperty("cat_id")
            private String catId;
            @JsonProperty("user_cat")
            private String userCat;
            @JsonProperty("user_id")
            private String userId;
            @JsonProperty("goods_sn")
            private String goodsSn;
            @JsonProperty("bar_code")
            private String barCode;
            @JsonProperty("goods_name")
            private String goodsName;
            @JsonProperty("goods_name_style")
            private String goodsNameStyle;
            @JsonProperty("click_count")
            private String clickCount;
            @JsonProperty("brand_id")
            private String brandId;
            @JsonProperty("provider_name")
            private String providerName;
            @JsonProperty("goods_number")
            private String goodsNumber;
            @JsonProperty("goods_weight")
            private String goodsWeight;
            @JsonProperty("default_shipping")
            private String defaultShipping;
            @JsonProperty("market_price")
            private String marketPrice;
            @JsonProperty("cost_price")
            private String costPrice;
            @JsonProperty("shop_price")
            private String shopPrice;
            @JsonProperty("promote_price")
            private String promotePrice;
            @JsonProperty("promote_start_date")
            private String promoteStartDate;
            @JsonProperty("promote_end_date")
            private String promoteEndDate;
            @JsonProperty("warn_number")
            private String warnNumber;
            @JsonProperty("keywords")
            private String keywords;
            @JsonProperty("goods_brief")
            private String goodsBrief;
            @JsonProperty("goods_desc")
            private String goodsDesc;
            @JsonProperty("desc_mobile")
            private String descMobile;
            @JsonProperty("goods_thumb")
            private String goodsThumb;
            @JsonProperty("goods_img")
            private String goodsImg;
            @JsonProperty("original_img")
            private String originalImg;
            @JsonProperty("is_real")
            private String isReal;
            @JsonProperty("extension_code")
            private String extensionCode;
            @JsonProperty("is_on_sale")
            private String isOnSale;
            @JsonProperty("is_alone_sale")
            private String isAloneSale;
            @JsonProperty("is_shipping")
            private String isShipping;
            @JsonProperty("integral")
            private String integral;
            @JsonProperty("add_time")
            private String addTime;
            @JsonProperty("sort_order")
            private String sortOrder;
            @JsonProperty("is_delete")
            private String isDelete;
            @JsonProperty("is_best")
            private String isBest;
            @JsonProperty("is_new")
            private String isNew;
            @JsonProperty("is_hot")
            private String isHot;
            @JsonProperty("is_promote")
            private String isPromote;
            @JsonProperty("is_volume")
            private String isVolume;
            @JsonProperty("is_fullcut")
            private String isFullcut;
            @JsonProperty("bonus_type_id")
            private String bonusTypeId;
            @JsonProperty("last_update")
            private String lastUpdate;
            @JsonProperty("goods_type")
            private String goodsType;
            @JsonProperty("seller_note")
            private String sellerNote;
            @JsonProperty("give_integral")
            private String giveIntegral;
            @JsonProperty("rank_integral")
            private String rankIntegral;
            @JsonProperty("suppliers_id")
            private String suppliersId;
            @JsonProperty("is_check")
            private Object isCheck;
            @JsonProperty("store_hot")
            private String storeHot;
            @JsonProperty("store_new")
            private String storeNew;
            @JsonProperty("store_best")
            private String storeBest;
            @JsonProperty("group_number")
            private String groupNumber;
            @JsonProperty("is_xiangou")
            private String isXiangou;
            @JsonProperty("xiangou_start_date")
            private String xiangouStartDate;
            @JsonProperty("xiangou_end_date")
            private String xiangouEndDate;
            @JsonProperty("xiangou_num")
            private String xiangouNum;
            @JsonProperty("review_status")
            private String reviewStatus;
            @JsonProperty("review_content")
            private String reviewContent;
            @JsonProperty("goods_shipai")
            private String goodsShipai;
            @JsonProperty("comments_number")
            private String commentsNumber;
            @JsonProperty("sales_volume")
            private String salesVolume;
            @JsonProperty("comment_num")
            private String commentNum;
            @JsonProperty("model_price")
            private String modelPrice;
            @JsonProperty("model_inventory")
            private String modelInventory;
            @JsonProperty("model_attr")
            private String modelAttr;
            @JsonProperty("largest_amount")
            private String largestAmount;
            @JsonProperty("pinyin_keyword")
            private String pinyinKeyword;
            @JsonProperty("goods_product_tag")
            private String goodsProductTag;
            @JsonProperty("goods_tag")
            private Object goodsTag;
            @JsonProperty("stages")
            private String stages;
            @JsonProperty("stages_rate")
            private String stagesRate;
            @JsonProperty("freight")
            private String freight;
            @JsonProperty("shipping_fee")
            private String shippingFee;
            @JsonProperty("tid")
            private String tid;
            @JsonProperty("goods_unit")
            private String goodsUnit;
            @JsonProperty("goods_cause")
            private String goodsCause;
            @JsonProperty("dis_commission")
            private String disCommission;
            @JsonProperty("is_distribution")
            private String isDistribution;
            @JsonProperty("commission_rate")
            private String commissionRate;
            @JsonProperty("from_seller")
            private String fromSeller;
            @JsonProperty("sales_volume_base")
            private String salesVolumeBase;
            @JsonProperty("user_brand")
            private String userBrand;
            @JsonProperty("product_table")
            private String productTable;
            @JsonProperty("product_id")
            private String productId;
            @JsonProperty("product_price")
            private String productPrice;
            @JsonProperty("product_promote_price")
            private String productPromotePrice;
            @JsonProperty("goods_video")
            private String goodsVideo;

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getCatId() {
                return catId;
            }

            public void setCatId(String catId) {
                this.catId = catId;
            }

            public String getUserCat() {
                return userCat;
            }

            public void setUserCat(String userCat) {
                this.userCat = userCat;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getGoodsSn() {
                return goodsSn;
            }

            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }

            public String getBarCode() {
                return barCode;
            }

            public void setBarCode(String barCode) {
                this.barCode = barCode;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsNameStyle() {
                return goodsNameStyle;
            }

            public void setGoodsNameStyle(String goodsNameStyle) {
                this.goodsNameStyle = goodsNameStyle;
            }

            public String getClickCount() {
                return clickCount;
            }

            public void setClickCount(String clickCount) {
                this.clickCount = clickCount;
            }

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getProviderName() {
                return providerName;
            }

            public void setProviderName(String providerName) {
                this.providerName = providerName;
            }

            public String getGoodsNumber() {
                return goodsNumber;
            }

            public void setGoodsNumber(String goodsNumber) {
                this.goodsNumber = goodsNumber;
            }

            public String getGoodsWeight() {
                return goodsWeight;
            }

            public void setGoodsWeight(String goodsWeight) {
                this.goodsWeight = goodsWeight;
            }

            public String getDefaultShipping() {
                return defaultShipping;
            }

            public void setDefaultShipping(String defaultShipping) {
                this.defaultShipping = defaultShipping;
            }

            public String getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(String marketPrice) {
                this.marketPrice = marketPrice;
            }

            public String getCostPrice() {
                return costPrice;
            }

            public void setCostPrice(String costPrice) {
                this.costPrice = costPrice;
            }

            public String getShopPrice() {
                return shopPrice;
            }

            public void setShopPrice(String shopPrice) {
                this.shopPrice = shopPrice;
            }

            public String getPromotePrice() {
                return promotePrice;
            }

            public void setPromotePrice(String promotePrice) {
                this.promotePrice = promotePrice;
            }

            public String getPromoteStartDate() {
                return promoteStartDate;
            }

            public void setPromoteStartDate(String promoteStartDate) {
                this.promoteStartDate = promoteStartDate;
            }

            public String getPromoteEndDate() {
                return promoteEndDate;
            }

            public void setPromoteEndDate(String promoteEndDate) {
                this.promoteEndDate = promoteEndDate;
            }

            public String getWarnNumber() {
                return warnNumber;
            }

            public void setWarnNumber(String warnNumber) {
                this.warnNumber = warnNumber;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getGoodsBrief() {
                return goodsBrief;
            }

            public void setGoodsBrief(String goodsBrief) {
                this.goodsBrief = goodsBrief;
            }

            public String getGoodsDesc() {
                return goodsDesc;
            }

            public void setGoodsDesc(String goodsDesc) {
                this.goodsDesc = goodsDesc;
            }

            public String getDescMobile() {
                return descMobile;
            }

            public void setDescMobile(String descMobile) {
                this.descMobile = descMobile;
            }

            public String getGoodsThumb() {
                return goodsThumb;
            }

            public void setGoodsThumb(String goodsThumb) {
                this.goodsThumb = goodsThumb;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getOriginalImg() {
                return originalImg;
            }

            public void setOriginalImg(String originalImg) {
                this.originalImg = originalImg;
            }

            public String getIsReal() {
                return isReal;
            }

            public void setIsReal(String isReal) {
                this.isReal = isReal;
            }

            public String getExtensionCode() {
                return extensionCode;
            }

            public void setExtensionCode(String extensionCode) {
                this.extensionCode = extensionCode;
            }

            public String getIsOnSale() {
                return isOnSale;
            }

            public void setIsOnSale(String isOnSale) {
                this.isOnSale = isOnSale;
            }

            public String getIsAloneSale() {
                return isAloneSale;
            }

            public void setIsAloneSale(String isAloneSale) {
                this.isAloneSale = isAloneSale;
            }

            public String getIsShipping() {
                return isShipping;
            }

            public void setIsShipping(String isShipping) {
                this.isShipping = isShipping;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(String sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getIsBest() {
                return isBest;
            }

            public void setIsBest(String isBest) {
                this.isBest = isBest;
            }

            public String getIsNew() {
                return isNew;
            }

            public void setIsNew(String isNew) {
                this.isNew = isNew;
            }

            public String getIsHot() {
                return isHot;
            }

            public void setIsHot(String isHot) {
                this.isHot = isHot;
            }

            public String getIsPromote() {
                return isPromote;
            }

            public void setIsPromote(String isPromote) {
                this.isPromote = isPromote;
            }

            public String getIsVolume() {
                return isVolume;
            }

            public void setIsVolume(String isVolume) {
                this.isVolume = isVolume;
            }

            public String getIsFullcut() {
                return isFullcut;
            }

            public void setIsFullcut(String isFullcut) {
                this.isFullcut = isFullcut;
            }

            public String getBonusTypeId() {
                return bonusTypeId;
            }

            public void setBonusTypeId(String bonusTypeId) {
                this.bonusTypeId = bonusTypeId;
            }

            public String getLastUpdate() {
                return lastUpdate;
            }

            public void setLastUpdate(String lastUpdate) {
                this.lastUpdate = lastUpdate;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }

            public String getSellerNote() {
                return sellerNote;
            }

            public void setSellerNote(String sellerNote) {
                this.sellerNote = sellerNote;
            }

            public String getGiveIntegral() {
                return giveIntegral;
            }

            public void setGiveIntegral(String giveIntegral) {
                this.giveIntegral = giveIntegral;
            }

            public String getRankIntegral() {
                return rankIntegral;
            }

            public void setRankIntegral(String rankIntegral) {
                this.rankIntegral = rankIntegral;
            }

            public String getSuppliersId() {
                return suppliersId;
            }

            public void setSuppliersId(String suppliersId) {
                this.suppliersId = suppliersId;
            }

            public Object getIsCheck() {
                return isCheck;
            }

            public void setIsCheck(Object isCheck) {
                this.isCheck = isCheck;
            }

            public String getStoreHot() {
                return storeHot;
            }

            public void setStoreHot(String storeHot) {
                this.storeHot = storeHot;
            }

            public String getStoreNew() {
                return storeNew;
            }

            public void setStoreNew(String storeNew) {
                this.storeNew = storeNew;
            }

            public String getStoreBest() {
                return storeBest;
            }

            public void setStoreBest(String storeBest) {
                this.storeBest = storeBest;
            }

            public String getGroupNumber() {
                return groupNumber;
            }

            public void setGroupNumber(String groupNumber) {
                this.groupNumber = groupNumber;
            }

            public String getIsXiangou() {
                return isXiangou;
            }

            public void setIsXiangou(String isXiangou) {
                this.isXiangou = isXiangou;
            }

            public String getXiangouStartDate() {
                return xiangouStartDate;
            }

            public void setXiangouStartDate(String xiangouStartDate) {
                this.xiangouStartDate = xiangouStartDate;
            }

            public String getXiangouEndDate() {
                return xiangouEndDate;
            }

            public void setXiangouEndDate(String xiangouEndDate) {
                this.xiangouEndDate = xiangouEndDate;
            }

            public String getXiangouNum() {
                return xiangouNum;
            }

            public void setXiangouNum(String xiangouNum) {
                this.xiangouNum = xiangouNum;
            }

            public String getReviewStatus() {
                return reviewStatus;
            }

            public void setReviewStatus(String reviewStatus) {
                this.reviewStatus = reviewStatus;
            }

            public String getReviewContent() {
                return reviewContent;
            }

            public void setReviewContent(String reviewContent) {
                this.reviewContent = reviewContent;
            }

            public String getGoodsShipai() {
                return goodsShipai;
            }

            public void setGoodsShipai(String goodsShipai) {
                this.goodsShipai = goodsShipai;
            }

            public String getCommentsNumber() {
                return commentsNumber;
            }

            public void setCommentsNumber(String commentsNumber) {
                this.commentsNumber = commentsNumber;
            }

            public String getSalesVolume() {
                return salesVolume;
            }

            public void setSalesVolume(String salesVolume) {
                this.salesVolume = salesVolume;
            }

            public String getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(String commentNum) {
                this.commentNum = commentNum;
            }

            public String getModelPrice() {
                return modelPrice;
            }

            public void setModelPrice(String modelPrice) {
                this.modelPrice = modelPrice;
            }

            public String getModelInventory() {
                return modelInventory;
            }

            public void setModelInventory(String modelInventory) {
                this.modelInventory = modelInventory;
            }

            public String getModelAttr() {
                return modelAttr;
            }

            public void setModelAttr(String modelAttr) {
                this.modelAttr = modelAttr;
            }

            public String getLargestAmount() {
                return largestAmount;
            }

            public void setLargestAmount(String largestAmount) {
                this.largestAmount = largestAmount;
            }

            public String getPinyinKeyword() {
                return pinyinKeyword;
            }

            public void setPinyinKeyword(String pinyinKeyword) {
                this.pinyinKeyword = pinyinKeyword;
            }

            public String getGoodsProductTag() {
                return goodsProductTag;
            }

            public void setGoodsProductTag(String goodsProductTag) {
                this.goodsProductTag = goodsProductTag;
            }

            public Object getGoodsTag() {
                return goodsTag;
            }

            public void setGoodsTag(Object goodsTag) {
                this.goodsTag = goodsTag;
            }

            public String getStages() {
                return stages;
            }

            public void setStages(String stages) {
                this.stages = stages;
            }

            public String getStagesRate() {
                return stagesRate;
            }

            public void setStagesRate(String stagesRate) {
                this.stagesRate = stagesRate;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getShippingFee() {
                return shippingFee;
            }

            public void setShippingFee(String shippingFee) {
                this.shippingFee = shippingFee;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getGoodsUnit() {
                return goodsUnit;
            }

            public void setGoodsUnit(String goodsUnit) {
                this.goodsUnit = goodsUnit;
            }

            public String getGoodsCause() {
                return goodsCause;
            }

            public void setGoodsCause(String goodsCause) {
                this.goodsCause = goodsCause;
            }

            public String getDisCommission() {
                return disCommission;
            }

            public void setDisCommission(String disCommission) {
                this.disCommission = disCommission;
            }

            public String getIsDistribution() {
                return isDistribution;
            }

            public void setIsDistribution(String isDistribution) {
                this.isDistribution = isDistribution;
            }

            public String getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(String commissionRate) {
                this.commissionRate = commissionRate;
            }

            public String getFromSeller() {
                return fromSeller;
            }

            public void setFromSeller(String fromSeller) {
                this.fromSeller = fromSeller;
            }

            public String getSalesVolumeBase() {
                return salesVolumeBase;
            }

            public void setSalesVolumeBase(String salesVolumeBase) {
                this.salesVolumeBase = salesVolumeBase;
            }

            public String getUserBrand() {
                return userBrand;
            }

            public void setUserBrand(String userBrand) {
                this.userBrand = userBrand;
            }

            public String getProductTable() {
                return productTable;
            }

            public void setProductTable(String productTable) {
                this.productTable = productTable;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }

            public String getProductPromotePrice() {
                return productPromotePrice;
            }

            public void setProductPromotePrice(String productPromotePrice) {
                this.productPromotePrice = productPromotePrice;
            }

            public String getGoodsVideo() {
                return goodsVideo;
            }

            public void setGoodsVideo(String goodsVideo) {
                this.goodsVideo = goodsVideo;
            }
        }
    }
}
