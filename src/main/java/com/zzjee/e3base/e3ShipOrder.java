package com.zzjee.e3base;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonAutoDetect
public class e3ShipOrder {


    /**
     * status : api-success
     * message : success
     * data : {"orderDetail":[{"order_sn":"18062801151515","shipping_sn":"12345678","add_time":"1530147171","skuList":[{"goods_sn":"Z0101033","num":"1","sku":"Z0101033","color_code":"0","color_name":"0"},{"goods_sn":"BB2001","num":"1","sku":"BB2001M01531740","color_code":"M01","color_name":"M01"}]},{"order_sn":"17082101149794","shipping_sn":"20171017003","add_time":"1503299464","skuList":[{"goods_sn":"QN8001","num":"1","sku":"QN8001B11","color_code":"B11","color_name":"B11"},{"goods_sn":"QN8001","num":"1","sku":"QN8001B70","color_code":"B70","color_name":"B70"}]},{"order_sn":"17053100345423","shipping_sn":"613890633385","add_time":"1496185201","skuList":[{"goods_sn":"BL2531","num":"1","sku":"BL2531M01","color_code":"M01","color_name":"M01"}]}]}
     */

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @JsonProperty("orderDetail")
        private List<OrderDetailBean> orderDetail;

        public List<OrderDetailBean> getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(List<OrderDetailBean> orderDetail) {
            this.orderDetail = orderDetail;
        }

        public static class OrderDetailBean {
            /**
             * order_sn : 18062801151515
             * shipping_sn : 12345678
             * add_time : 1530147171
             * skuList : [{"goods_sn":"Z0101033","num":"1","sku":"Z0101033","color_code":"0","color_name":"0"},{"goods_sn":"BB2001","num":"1","sku":"BB2001M01531740","color_code":"M01","color_name":"M01"}]
             */

            @JsonProperty("order_sn")
            private String orderSn;
            @JsonProperty("shipping_sn")
            private String shippingSn;
            @JsonProperty("add_time")
            private String addTime;
            @JsonProperty("skuList")
            private List<SkuListBean> skuList;

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getShippingSn() {
                return shippingSn;
            }

            public void setShippingSn(String shippingSn) {
                this.shippingSn = shippingSn;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public List<SkuListBean> getSkuList() {
                return skuList;
            }

            public void setSkuList(List<SkuListBean> skuList) {
                this.skuList = skuList;
            }

            public static class SkuListBean {
                /**
                 * goods_sn : Z0101033
                 * num : 1
                 * sku : Z0101033
                 * color_code : 0
                 * color_name : 0
                 */

                @JsonProperty("goods_sn")
                private String goodsSn;
                @JsonProperty("num")
                private String num;
                @JsonProperty("sku")
                private String sku;
                @JsonProperty("color_code")
                private String colorCode;
                @JsonProperty("color_name")
                private String colorName;

                public String getGoodsSn() {
                    return goodsSn;
                }

                public void setGoodsSn(String goodsSn) {
                    this.goodsSn = goodsSn;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getColorCode() {
                    return colorCode;
                }

                public void setColorCode(String colorCode) {
                    this.colorCode = colorCode;
                }

                public String getColorName() {
                    return colorName;
                }

                public void setColorName(String colorName) {
                    this.colorName = colorName;
                }
            }
        }
    }
}
