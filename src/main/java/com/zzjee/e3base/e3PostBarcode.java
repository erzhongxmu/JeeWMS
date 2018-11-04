package com.zzjee.e3base;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonAutoDetect
public class e3PostBarcode {

    /**
     * order_sn  : 123456
     * ck_time  : 2018-08-01 00:00:00
     * orderDetail : [{"sku":"111122222","unique_code":"123456","security_code ":"12345678","ck_code ":"001","type":"2"," sku_storage":"1111122222"},{"sku":"111122222","unique_code":"123456","security_code ":"12345678","ck_code ":"001","type":"2"," sku_storage":"1111122222"}]
     */

    @JsonProperty("order_sn ")
    private String orderSn;
    @JsonProperty("ck_time ")
    private String ckTime;
    @JsonProperty("orderDetail")
    private List<OrderDetailBean> orderDetail;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getCkTime() {
        return ckTime;
    }

    public void setCkTime(String ckTime) {
        this.ckTime = ckTime;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class OrderDetailBean {
        /**
         * sku : 111122222
         * unique_code : 123456
         * security_code  : 12345678
         * ck_code  : 001
         * type : 2
         *  sku_storage : 1111122222
         */

        @JsonProperty("sku")
        private String sku;
        @JsonProperty("unique_code")
        private String uniqueCode;
        @JsonProperty("security_code ")
        private String securityCode;
        @JsonProperty("ck_code ")
        private String ckCode;
        @JsonProperty("type")
        private String type;
        @JsonProperty(" sku_storage")
        private String skuStorage;

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getUniqueCode() {
            return uniqueCode;
        }

        public void setUniqueCode(String uniqueCode) {
            this.uniqueCode = uniqueCode;
        }

        public String getSecurityCode() {
            return securityCode;
        }

        public void setSecurityCode(String securityCode) {
            this.securityCode = securityCode;
        }

        public String getCkCode() {
            return ckCode;
        }

        public void setCkCode(String ckCode) {
            this.ckCode = ckCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSkuStorage() {
            return skuStorage;
        }

        public void setSkuStorage(String skuStorage) {
            this.skuStorage = skuStorage;
        }
    }
}
