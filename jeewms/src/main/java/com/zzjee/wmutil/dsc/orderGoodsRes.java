package com.zzjee.wmutil.dsc;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class orderGoodsRes {


    /**
     * result : success
     * error : 0
     * msg : 成功获取数据
     * info : {"list":[{"rec_id":"6","order_id":"9","order_sn":null,"user_id":"0","cart_recid":"","goods_id":"923","goods_name":"YSL/圣罗兰 莹亮纯魅唇膏","goods_sn":"ECS000923","product_id":"0","goods_number":"2","market_price":"320.00","goods_price":"171.00","goods_attr":"","send_number":"0","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","model_attr":"0","goods_attr_id":"","ru_id":"0","shopping_fee":"0.00","warehouse_id":"1","area_id":"4","is_single":"0","freight":"0","tid":"0","shipping_fee":"0.00","drp_money":"0.00","is_distribution":"0","commission_rate":"0","stages_qishu":"-1"},{"rec_id":"7","order_id":"10","order_sn":null,"user_id":"0","cart_recid":"","goods_id":"922","goods_name":"MAC/魅可 立体绒光修容饼","goods_sn":"ECS000922","product_id":"0","goods_number":"10","market_price":"340.00","goods_price":"173.00","goods_attr":"","send_number":"0","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","model_attr":"0","goods_attr_id":"","ru_id":"0","shopping_fee":"0.00","warehouse_id":"0","area_id":"0","is_single":"0","freight":"0","tid":"0","shipping_fee":"0.00","drp_money":"0.00","is_distribution":"0","commission_rate":"0","stages_qishu":"-1"},{"rec_id":"8","order_id":"17","order_sn":null,"user_id":"0","cart_recid":"","goods_id":"2345","goods_name":"爱他美3段婴儿奶粉900克","goods_sn":"PGT002345","product_id":"0","goods_number":"1","market_price":"111.60","goods_price":"93.00","goods_attr":"","send_number":"0","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","model_attr":"0","goods_attr_id":"","ru_id":"0","shopping_fee":"0.00","warehouse_id":"0","area_id":"0","is_single":"0","freight":"0","tid":"0","shipping_fee":"0.00","drp_money":"0.00","is_distribution":"0","commission_rate":"0","stages_qishu":"-1"}],"filter":{"page":1,"page_size":15,"record_count":"3","page_count":1},"page_count":1,"record_count":"3"}
     */

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
        /**
         * list : [{"rec_id":"6","order_id":"9","order_sn":null,"user_id":"0","cart_recid":"","goods_id":"923","goods_name":"YSL/圣罗兰 莹亮纯魅唇膏","goods_sn":"ECS000923","product_id":"0","goods_number":"2","market_price":"320.00","goods_price":"171.00","goods_attr":"","send_number":"0","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","model_attr":"0","goods_attr_id":"","ru_id":"0","shopping_fee":"0.00","warehouse_id":"1","area_id":"4","is_single":"0","freight":"0","tid":"0","shipping_fee":"0.00","drp_money":"0.00","is_distribution":"0","commission_rate":"0","stages_qishu":"-1"},{"rec_id":"7","order_id":"10","order_sn":null,"user_id":"0","cart_recid":"","goods_id":"922","goods_name":"MAC/魅可 立体绒光修容饼","goods_sn":"ECS000922","product_id":"0","goods_number":"10","market_price":"340.00","goods_price":"173.00","goods_attr":"","send_number":"0","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","model_attr":"0","goods_attr_id":"","ru_id":"0","shopping_fee":"0.00","warehouse_id":"0","area_id":"0","is_single":"0","freight":"0","tid":"0","shipping_fee":"0.00","drp_money":"0.00","is_distribution":"0","commission_rate":"0","stages_qishu":"-1"},{"rec_id":"8","order_id":"17","order_sn":null,"user_id":"0","cart_recid":"","goods_id":"2345","goods_name":"爱他美3段婴儿奶粉900克","goods_sn":"PGT002345","product_id":"0","goods_number":"1","market_price":"111.60","goods_price":"93.00","goods_attr":"","send_number":"0","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","model_attr":"0","goods_attr_id":"","ru_id":"0","shopping_fee":"0.00","warehouse_id":"0","area_id":"0","is_single":"0","freight":"0","tid":"0","shipping_fee":"0.00","drp_money":"0.00","is_distribution":"0","commission_rate":"0","stages_qishu":"-1"}]
         * filter : {"page":1,"page_size":15,"record_count":"3","page_count":1}
         * page_count : 1
         * record_count : 3
         */

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
             * record_count : 3
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
            /**
             * rec_id : 6
             * order_id : 9
             * order_sn : null
             * user_id : 0
             * cart_recid :
             * goods_id : 923
             * goods_name : YSL/圣罗兰 莹亮纯魅唇膏
             * goods_sn : ECS000923
             * product_id : 0
             * goods_number : 2
             * market_price : 320.00
             * goods_price : 171.00
             * goods_attr :
             * send_number : 0
             * is_real : 1
             * extension_code :
             * parent_id : 0
             * is_gift : 0
             * model_attr : 0
             * goods_attr_id :
             * ru_id : 0
             * shopping_fee : 0.00
             * warehouse_id : 1
             * area_id : 4
             * is_single : 0
             * freight : 0
             * tid : 0
             * shipping_fee : 0.00
             * drp_money : 0.00
             * is_distribution : 0
             * commission_rate : 0
             * stages_qishu : -1
             */

            @JsonProperty("rec_id")
            private String recId;
            @JsonProperty("order_id")
            private String orderId;
            @JsonProperty("order_sn")
            private Object orderSn;
            @JsonProperty("user_id")
            private String userId;
            @JsonProperty("cart_recid")
            private String cartRecid;
            @JsonProperty("goods_id")
            private String goodsId;
            @JsonProperty("goods_name")
            private String goodsName;
            @JsonProperty("goods_sn")
            private String goodsSn;
            @JsonProperty("product_id")
            private String productId;
            @JsonProperty("goods_number")
            private String goodsNumber;
            @JsonProperty("market_price")
            private String marketPrice;
            @JsonProperty("goods_price")
            private String goodsPrice;
            @JsonProperty("goods_attr")
            private String goodsAttr;
            @JsonProperty("send_number")
            private String sendNumber;
            @JsonProperty("is_real")
            private String isReal;
            @JsonProperty("extension_code")
            private String extensionCode;
            @JsonProperty("parent_id")
            private String parentId;
            @JsonProperty("is_gift")
            private String isGift;
            @JsonProperty("model_attr")
            private String modelAttr;
            @JsonProperty("goods_attr_id")
            private String goodsAttrId;
            @JsonProperty("ru_id")
            private String ruId;
            @JsonProperty("shopping_fee")
            private String shoppingFee;
            @JsonProperty("warehouse_id")
            private String warehouseId;
            @JsonProperty("area_id")
            private String areaId;
            @JsonProperty("is_single")
            private String isSingle;
            @JsonProperty("freight")
            private String freight;
            @JsonProperty("tid")
            private String tid;
            @JsonProperty("shipping_fee")
            private String shippingFee;
            @JsonProperty("drp_money")
            private String drpMoney;
            @JsonProperty("is_distribution")
            private String isDistribution;
            @JsonProperty("commission_rate")
            private String commissionRate;
            @JsonProperty("stages_qishu")
            private String stagesQishu;

            public String getRecId() {
                return recId;
            }

            public void setRecId(String recId) {
                this.recId = recId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public Object getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(Object orderSn) {
                this.orderSn = orderSn;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getCartRecid() {
                return cartRecid;
            }

            public void setCartRecid(String cartRecid) {
                this.cartRecid = cartRecid;
            }

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

            public String getGoodsSn() {
                return goodsSn;
            }

            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getGoodsNumber() {
                return goodsNumber;
            }

            public void setGoodsNumber(String goodsNumber) {
                this.goodsNumber = goodsNumber;
            }

            public String getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(String marketPrice) {
                this.marketPrice = marketPrice;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsAttr() {
                return goodsAttr;
            }

            public void setGoodsAttr(String goodsAttr) {
                this.goodsAttr = goodsAttr;
            }

            public String getSendNumber() {
                return sendNumber;
            }

            public void setSendNumber(String sendNumber) {
                this.sendNumber = sendNumber;
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

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getIsGift() {
                return isGift;
            }

            public void setIsGift(String isGift) {
                this.isGift = isGift;
            }

            public String getModelAttr() {
                return modelAttr;
            }

            public void setModelAttr(String modelAttr) {
                this.modelAttr = modelAttr;
            }

            public String getGoodsAttrId() {
                return goodsAttrId;
            }

            public void setGoodsAttrId(String goodsAttrId) {
                this.goodsAttrId = goodsAttrId;
            }

            public String getRuId() {
                return ruId;
            }

            public void setRuId(String ruId) {
                this.ruId = ruId;
            }

            public String getShoppingFee() {
                return shoppingFee;
            }

            public void setShoppingFee(String shoppingFee) {
                this.shoppingFee = shoppingFee;
            }

            public String getWarehouseId() {
                return warehouseId;
            }

            public void setWarehouseId(String warehouseId) {
                this.warehouseId = warehouseId;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getIsSingle() {
                return isSingle;
            }

            public void setIsSingle(String isSingle) {
                this.isSingle = isSingle;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getShippingFee() {
                return shippingFee;
            }

            public void setShippingFee(String shippingFee) {
                this.shippingFee = shippingFee;
            }

            public String getDrpMoney() {
                return drpMoney;
            }

            public void setDrpMoney(String drpMoney) {
                this.drpMoney = drpMoney;
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

            public String getStagesQishu() {
                return stagesQishu;
            }

            public void setStagesQishu(String stagesQishu) {
                this.stagesQishu = stagesQishu;
            }
        }
    }
}
