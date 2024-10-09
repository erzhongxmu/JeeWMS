package com.zzjee.wmutil.dsc;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class orderListRes {



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
             * record_count : 1
             * page_count : 1
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


            @JsonProperty("order_id")
            private String orderId;
            @JsonProperty("main_order_id")
            private String mainOrderId;
            @JsonProperty("order_sn")
            private String orderSn;
            @JsonProperty("user_id")
            private String userId;
            @JsonProperty("order_status")
            private String orderStatus;
            @JsonProperty("shipping_status")
            private String shippingStatus;
            @JsonProperty("pay_status")
            private String payStatus;
            @JsonProperty("consignee")
            private String consignee;
            @JsonProperty("country")
            private String country;
            @JsonProperty("province")
            private String province;
            @JsonProperty("city")
            private String city;
            @JsonProperty("district")
            private String district;
            @JsonProperty("street")
            private String street;
            @JsonProperty("address")
            private String address;
            @JsonProperty("zipcode")
            private String zipcode;
            @JsonProperty("tel")
            private String tel;
            @JsonProperty("mobile")
            private String mobile;
            @JsonProperty("email")
            private String email;
            @JsonProperty("best_time")
            private String bestTime;
            @JsonProperty("sign_building")
            private String signBuilding;
            @JsonProperty("postscript")
            private String postscript;
            @JsonProperty("shipping_id")
            private String shippingId;
            @JsonProperty("shipping_name")
            private String shippingName;
            @JsonProperty("shipping_code")
            private String shippingCode;
            @JsonProperty("shipping_type")
            private String shippingType;
            @JsonProperty("pay_id")
            private String payId;
            @JsonProperty("pay_name")
            private String payName;
            @JsonProperty("how_oos")
            private String howOos;
            @JsonProperty("how_surplus")
            private String howSurplus;
            @JsonProperty("pack_name")
            private String packName;
            @JsonProperty("card_name")
            private String cardName;
            @JsonProperty("card_message")
            private String cardMessage;
            @JsonProperty("inv_payee")
            private String invPayee;
            @JsonProperty("inv_content")
            private String invContent;
            @JsonProperty("goods_amount")
            private String goodsAmount;
            @JsonProperty("cost_amount")
            private String costAmount;
            @JsonProperty("shipping_fee")
            private String shippingFee;
            @JsonProperty("insure_fee")
            private String insureFee;
            @JsonProperty("pay_fee")
            private String payFee;
            @JsonProperty("pack_fee")
            private String packFee;
            @JsonProperty("card_fee")
            private String cardFee;
            @JsonProperty("money_paid")
            private String moneyPaid;
            @JsonProperty("surplus")
            private String surplus;
            @JsonProperty("integral")
            private String integral;
            @JsonProperty("integral_money")
            private String integralMoney;
            @JsonProperty("bonus")
            private String bonus;
            @JsonProperty("order_amount")
            private String orderAmount;
            @JsonProperty("return_amount")
            private String returnAmount;
            @JsonProperty("from_ad")
            private String fromAd;
            @JsonProperty("referer")
            private String referer;
            @JsonProperty("add_time")
            private String addTime;
            @JsonProperty("confirm_time")
            private String confirmTime;
            @JsonProperty("pay_time")
            private String payTime;
            @JsonProperty("shipping_time")
            private String shippingTime;
            @JsonProperty("confirm_take_time")
            private String confirmTakeTime;
            @JsonProperty("auto_delivery_time")
            private String autoDeliveryTime;
            @JsonProperty("pack_id")
            private String packId;
            @JsonProperty("card_id")
            private String cardId;
            @JsonProperty("bonus_id")
            private String bonusId;
            @JsonProperty("invoice_no")
            private String invoiceNo;
            @JsonProperty("extension_code")
            private String extensionCode;
            @JsonProperty("extension_id")
            private String extensionId;
            @JsonProperty("to_buyer")
            private String toBuyer;
            @JsonProperty("pay_note")
            private String payNote;
            @JsonProperty("agency_id")
            private String agencyId;
            @JsonProperty("inv_type")
            private String invType;
            @JsonProperty("tax")
            private String tax;
            @JsonProperty("is_separate")
            private String isSeparate;
            @JsonProperty("parent_id")
            private String parentId;
            @JsonProperty("discount")
            private String discount;
            @JsonProperty("discount_all")
            private String discountAll;
            @JsonProperty("is_delete")
            private String isDelete;
            @JsonProperty("is_settlement")
            private String isSettlement;
            @JsonProperty("sign_time")
            private Object signTime;
            @JsonProperty("is_single")
            private String isSingle;
            @JsonProperty("point_id")
            private String pointId;
            @JsonProperty("shipping_dateStr")
            private String shippingDateStr;
            @JsonProperty("supplier_id")
            private String supplierId;
            @JsonProperty("froms")
            private String froms;
            @JsonProperty("coupons")
            private String coupons;
            @JsonProperty("uc_id")
            private String ucId;
            @JsonProperty("is_zc_order")
            private String isZcOrder;
            @JsonProperty("zc_goods_id")
            private String zcGoodsId;
            @JsonProperty("is_frozen")
            private String isFrozen;
            @JsonProperty("chargeoff_status")
            private String chargeoffStatus;
            @JsonProperty("invoice_type")
            private String invoiceType;
            @JsonProperty("vat_id")
            private String vatId;
            @JsonProperty("drp_is_separate")
            private String drpIsSeparate;
            @JsonProperty("team_id")
            private String teamId;
            @JsonProperty("team_parent_id")
            private String teamParentId;
            @JsonProperty("team_user_id")
            private String teamUserId;
            @JsonProperty("team_price")
            private String teamPrice;
            @JsonProperty("tax_id")
            private String taxId;
            @JsonProperty("is_update_sale")
            private String isUpdateSale;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getMainOrderId() {
                return mainOrderId;
            }

            public void setMainOrderId(String mainOrderId) {
                this.mainOrderId = mainOrderId;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getShippingStatus() {
                return shippingStatus;
            }

            public void setShippingStatus(String shippingStatus) {
                this.shippingStatus = shippingStatus;
            }

            public String getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(String payStatus) {
                this.payStatus = payStatus;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getBestTime() {
                return bestTime;
            }

            public void setBestTime(String bestTime) {
                this.bestTime = bestTime;
            }

            public String getSignBuilding() {
                return signBuilding;
            }

            public void setSignBuilding(String signBuilding) {
                this.signBuilding = signBuilding;
            }

            public String getPostscript() {
                return postscript;
            }

            public void setPostscript(String postscript) {
                this.postscript = postscript;
            }

            public String getShippingId() {
                return shippingId;
            }

            public void setShippingId(String shippingId) {
                this.shippingId = shippingId;
            }

            public String getShippingName() {
                return shippingName;
            }

            public void setShippingName(String shippingName) {
                this.shippingName = shippingName;
            }

            public String getShippingCode() {
                return shippingCode;
            }

            public void setShippingCode(String shippingCode) {
                this.shippingCode = shippingCode;
            }

            public String getShippingType() {
                return shippingType;
            }

            public void setShippingType(String shippingType) {
                this.shippingType = shippingType;
            }

            public String getPayId() {
                return payId;
            }

            public void setPayId(String payId) {
                this.payId = payId;
            }

            public String getPayName() {
                return payName;
            }

            public void setPayName(String payName) {
                this.payName = payName;
            }

            public String getHowOos() {
                return howOos;
            }

            public void setHowOos(String howOos) {
                this.howOos = howOos;
            }

            public String getHowSurplus() {
                return howSurplus;
            }

            public void setHowSurplus(String howSurplus) {
                this.howSurplus = howSurplus;
            }

            public String getPackName() {
                return packName;
            }

            public void setPackName(String packName) {
                this.packName = packName;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public String getCardMessage() {
                return cardMessage;
            }

            public void setCardMessage(String cardMessage) {
                this.cardMessage = cardMessage;
            }

            public String getInvPayee() {
                return invPayee;
            }

            public void setInvPayee(String invPayee) {
                this.invPayee = invPayee;
            }

            public String getInvContent() {
                return invContent;
            }

            public void setInvContent(String invContent) {
                this.invContent = invContent;
            }

            public String getGoodsAmount() {
                return goodsAmount;
            }

            public void setGoodsAmount(String goodsAmount) {
                this.goodsAmount = goodsAmount;
            }

            public String getCostAmount() {
                return costAmount;
            }

            public void setCostAmount(String costAmount) {
                this.costAmount = costAmount;
            }

            public String getShippingFee() {
                return shippingFee;
            }

            public void setShippingFee(String shippingFee) {
                this.shippingFee = shippingFee;
            }

            public String getInsureFee() {
                return insureFee;
            }

            public void setInsureFee(String insureFee) {
                this.insureFee = insureFee;
            }

            public String getPayFee() {
                return payFee;
            }

            public void setPayFee(String payFee) {
                this.payFee = payFee;
            }

            public String getPackFee() {
                return packFee;
            }

            public void setPackFee(String packFee) {
                this.packFee = packFee;
            }

            public String getCardFee() {
                return cardFee;
            }

            public void setCardFee(String cardFee) {
                this.cardFee = cardFee;
            }

            public String getMoneyPaid() {
                return moneyPaid;
            }

            public void setMoneyPaid(String moneyPaid) {
                this.moneyPaid = moneyPaid;
            }

            public String getSurplus() {
                return surplus;
            }

            public void setSurplus(String surplus) {
                this.surplus = surplus;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getIntegralMoney() {
                return integralMoney;
            }

            public void setIntegralMoney(String integralMoney) {
                this.integralMoney = integralMoney;
            }

            public String getBonus() {
                return bonus;
            }

            public void setBonus(String bonus) {
                this.bonus = bonus;
            }

            public String getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(String orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getReturnAmount() {
                return returnAmount;
            }

            public void setReturnAmount(String returnAmount) {
                this.returnAmount = returnAmount;
            }

            public String getFromAd() {
                return fromAd;
            }

            public void setFromAd(String fromAd) {
                this.fromAd = fromAd;
            }

            public String getReferer() {
                return referer;
            }

            public void setReferer(String referer) {
                this.referer = referer;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getConfirmTime() {
                return confirmTime;
            }

            public void setConfirmTime(String confirmTime) {
                this.confirmTime = confirmTime;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getShippingTime() {
                return shippingTime;
            }

            public void setShippingTime(String shippingTime) {
                this.shippingTime = shippingTime;
            }

            public String getConfirmTakeTime() {
                return confirmTakeTime;
            }

            public void setConfirmTakeTime(String confirmTakeTime) {
                this.confirmTakeTime = confirmTakeTime;
            }

            public String getAutoDeliveryTime() {
                return autoDeliveryTime;
            }

            public void setAutoDeliveryTime(String autoDeliveryTime) {
                this.autoDeliveryTime = autoDeliveryTime;
            }

            public String getPackId() {
                return packId;
            }

            public void setPackId(String packId) {
                this.packId = packId;
            }

            public String getCardId() {
                return cardId;
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public String getBonusId() {
                return bonusId;
            }

            public void setBonusId(String bonusId) {
                this.bonusId = bonusId;
            }

            public String getInvoiceNo() {
                return invoiceNo;
            }

            public void setInvoiceNo(String invoiceNo) {
                this.invoiceNo = invoiceNo;
            }

            public String getExtensionCode() {
                return extensionCode;
            }

            public void setExtensionCode(String extensionCode) {
                this.extensionCode = extensionCode;
            }

            public String getExtensionId() {
                return extensionId;
            }

            public void setExtensionId(String extensionId) {
                this.extensionId = extensionId;
            }

            public String getToBuyer() {
                return toBuyer;
            }

            public void setToBuyer(String toBuyer) {
                this.toBuyer = toBuyer;
            }

            public String getPayNote() {
                return payNote;
            }

            public void setPayNote(String payNote) {
                this.payNote = payNote;
            }

            public String getAgencyId() {
                return agencyId;
            }

            public void setAgencyId(String agencyId) {
                this.agencyId = agencyId;
            }

            public String getInvType() {
                return invType;
            }

            public void setInvType(String invType) {
                this.invType = invType;
            }

            public String getTax() {
                return tax;
            }

            public void setTax(String tax) {
                this.tax = tax;
            }

            public String getIsSeparate() {
                return isSeparate;
            }

            public void setIsSeparate(String isSeparate) {
                this.isSeparate = isSeparate;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getDiscountAll() {
                return discountAll;
            }

            public void setDiscountAll(String discountAll) {
                this.discountAll = discountAll;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getIsSettlement() {
                return isSettlement;
            }

            public void setIsSettlement(String isSettlement) {
                this.isSettlement = isSettlement;
            }

            public Object getSignTime() {
                return signTime;
            }

            public void setSignTime(Object signTime) {
                this.signTime = signTime;
            }

            public String getIsSingle() {
                return isSingle;
            }

            public void setIsSingle(String isSingle) {
                this.isSingle = isSingle;
            }

            public String getPointId() {
                return pointId;
            }

            public void setPointId(String pointId) {
                this.pointId = pointId;
            }

            public String getShippingDateStr() {
                return shippingDateStr;
            }

            public void setShippingDateStr(String shippingDateStr) {
                this.shippingDateStr = shippingDateStr;
            }

            public String getSupplierId() {
                return supplierId;
            }

            public void setSupplierId(String supplierId) {
                this.supplierId = supplierId;
            }

            public String getFroms() {
                return froms;
            }

            public void setFroms(String froms) {
                this.froms = froms;
            }

            public String getCoupons() {
                return coupons;
            }

            public void setCoupons(String coupons) {
                this.coupons = coupons;
            }

            public String getUcId() {
                return ucId;
            }

            public void setUcId(String ucId) {
                this.ucId = ucId;
            }

            public String getIsZcOrder() {
                return isZcOrder;
            }

            public void setIsZcOrder(String isZcOrder) {
                this.isZcOrder = isZcOrder;
            }

            public String getZcGoodsId() {
                return zcGoodsId;
            }

            public void setZcGoodsId(String zcGoodsId) {
                this.zcGoodsId = zcGoodsId;
            }

            public String getIsFrozen() {
                return isFrozen;
            }

            public void setIsFrozen(String isFrozen) {
                this.isFrozen = isFrozen;
            }

            public String getChargeoffStatus() {
                return chargeoffStatus;
            }

            public void setChargeoffStatus(String chargeoffStatus) {
                this.chargeoffStatus = chargeoffStatus;
            }

            public String getInvoiceType() {
                return invoiceType;
            }

            public void setInvoiceType(String invoiceType) {
                this.invoiceType = invoiceType;
            }

            public String getVatId() {
                return vatId;
            }

            public void setVatId(String vatId) {
                this.vatId = vatId;
            }

            public String getDrpIsSeparate() {
                return drpIsSeparate;
            }

            public void setDrpIsSeparate(String drpIsSeparate) {
                this.drpIsSeparate = drpIsSeparate;
            }

            public String getTeamId() {
                return teamId;
            }

            public void setTeamId(String teamId) {
                this.teamId = teamId;
            }

            public String getTeamParentId() {
                return teamParentId;
            }

            public void setTeamParentId(String teamParentId) {
                this.teamParentId = teamParentId;
            }

            public String getTeamUserId() {
                return teamUserId;
            }

            public void setTeamUserId(String teamUserId) {
                this.teamUserId = teamUserId;
            }

            public String getTeamPrice() {
                return teamPrice;
            }

            public void setTeamPrice(String teamPrice) {
                this.teamPrice = teamPrice;
            }

            public String getTaxId() {
                return taxId;
            }

            public void setTaxId(String taxId) {
                this.taxId = taxId;
            }

            public String getIsUpdateSale() {
                return isUpdateSale;
            }

            public void setIsUpdateSale(String isUpdateSale) {
                this.isUpdateSale = isUpdateSale;
            }
        }
    }
}
