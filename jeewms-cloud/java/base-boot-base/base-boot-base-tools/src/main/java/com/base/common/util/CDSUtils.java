package com.base.common.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.common.base.dto.*;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * CDS海关报关工具
 */
@Slf4j
public class CDSUtils {

    Logger logger = LoggerFactory.getLogger(CDSUtils.class);

    private static final String URL = "http://39.105.50.75:9527";

    private static final String PRIVATE_KEY = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100bf3ea9fb1eebc88d54aeb853197f28a77a720b659f0f661e1fa6f98dd7aaae2818d2a9cf0598821ceac5597d827f72a1152d2406ec8da2bba377da7acea6815466710687bbe92ec606980d38268705d0b45fee3a973e2a11515ccacc31c3f692da8c31c7bc4c12d7d563aa1f31820eaab2596f7b64efd02ed580ccdda897c3a70203010001028180729a71b05bca3dc77bccac06984bdc27e8b778deefdf817961cc64d5bc3cbadaaa6d6b76f4ca7c8629a613c7a3b10e173ba26e697bce308bb31cfa9f07413e5434d9116ce5b6ddb37b68278a0a3ee3b427cbb1ab9ccf4afb33ad5ec9e9946301a8bff52b4fb201b6ffc6f049dbfa8da6aa9432137dfc60b306738e4a874b9411024100eae60bb45798e53dbbc3c91b2a2900cfe6e90dd5db366ca73b2e8a4b20e9036d033e169194b0abf047e7ad7b21c1f219b0dd3fd7988b8f5ac2d361afadaa805f024100d06cb675d17938754f4f4a20e5f6b71166e25648dbbe43d9218d3ac7732d8fa49d28034b2378e5a2f9a1f9e286bc67ad67c11bb056ec4d66610d3f98b76361b9024100b9028bc17525059983e406e790634f3270aaaaacffc573fa9b5062b81da9a97b190cb3f5e1e8ece3899ede32973a0c9753884f9a5d99be453c220554bb32097d02402a1dbda5032626a575d844d76f00b2af7e85bfffcaffa08030ca7415378dfe57ba360aac5547a9c78c00121997365d10ea45584e02d28f355fe9286fb8a7096902410096301d4b62b2dd420dd36b6ce319315a0aba7fa6e92690441fa9542603a0335e14d04616189dd1b14d641b57c7be1b93557775e2e252ddb4ea5c4ced147e70ed";
    private static final String PUBLIC_KEY = "30819f300d06092a864886f70d010101050003818d0030818902818100bf3ea9fb1eebc88d54aeb853197f28a77a720b659f0f661e1fa6f98dd7aaae2818d2a9cf0598821ceac5597d827f72a1152d2406ec8da2bba377da7acea6815466710687bbe92ec606980d38268705d0b45fee3a973e2a11515ccacc31c3f692da8c31c7bc4c12d7d563aa1f31820eaab2596f7b64efd02ed580ccdda897c3a70203010001";

    /**
     * CDS报关
     * @param cdsPushOrder
     * @return
     */
    public static String cdsPush(CDSPushOrder cdsPushOrder) {
        if (Objects.isNull(cdsPushOrder)) {
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        Map params = getPushOrderParams(cdsPushOrder);
        String requestJson = JSONObject.toJSONString(params);
        String sign = CDSRSA.sign(CDSRSA.getPrivateKey(PRIVATE_KEY), requestJson, "UTF-8");
        HttpHeaders headers = getHttpHeaders(sign, cdsPushOrder.getCompanyName());
        HttpEntity entity = new HttpEntity(params, headers);
        log.info("推送cds报关上送参数======={}",requestJson);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"/restful/mutualCDS/acceptOrder", entity, String.class);
        log.info("推送cds报关返回参数======={}", JSONObject.toJSONString(responseEntity));
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, String> responseMap = JSON.parseObject(responseEntity.getBody(), Map.class);
            if (MapUtil.getBool(responseMap,"success")) {
                return "success";
            }
            return responseMap.get("error_msg");
        }
        return "system error";
    }

    /**
     * CDS回执验证签名信息
     * @param cdsReceiptInfo
     * @param reqSign 返回回执的签名信息
     * @return
     */
    public static String cdsReceipt(CDSReceiptInfo cdsReceiptInfo, String reqSign) {
        if (Objects.isNull(cdsReceiptInfo) || StrUtil.isEmpty(reqSign)) {
            return "参数信息为空";
        }
        String requestJson = JSON.toJSONString(cdsReceiptInfo);
        try {
            CDSRSA.verify(CDSRSA.getPublicKey(PUBLIC_KEY),reqSign,requestJson,"utf-8");
        } catch (Exception e) {
            log.error("签名异常===",e);
            return "签名错误";
        }
//        RestTemplate restTemplate = new RestTemplate();
//        String requestJson = JSON.toJSONString(cdsReceiptInfo);
//        String sign = CDSRSA.sign(CDSRSA.getPrivateKey(PRIVATE_KEY), requestJson, "UTF-8");
//        HttpHeaders headers = getHttpHeaders(sign, null);
//        HttpEntity entity = new HttpEntity(requestJson, headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(null, entity, String.class);
//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            Map<String, String> responseMap = JSON.parseObject(responseEntity.getBody(), Map.class);
//            if ("true".equals(responseMap.get("success"))) {
//                // todo
//                return "success";
//            }
//            return responseMap.get("error_msg");
//        }
        return "success";
    }

    /**
     * CDS取消订单
     * @param cancelOrderInfo
     * @return
     */
    public static String cdsCancelOrder(CancelOrderInfo cancelOrderInfo) {
        if (Objects.isNull(cancelOrderInfo)) {
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        Map params = new HashMap(1024);
        params.put("company_name", cancelOrderInfo.getCompanyName());
        params.put("order_id", cancelOrderInfo.getOrderId());
        params.put("reason", cancelOrderInfo.getReason());
        String requestJson = JSON.toJSONString(params);
        String sign = CDSRSA.sign(CDSRSA.getPrivateKey(PRIVATE_KEY), requestJson, "UTF-8");
        HttpHeaders headers = getHttpHeaders(sign, cancelOrderInfo.getCompanyName());
        HttpEntity entity = new HttpEntity(requestJson, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"/restful/mutualCDS/cancelOrder", entity, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, String> responseMap = JSON.parseObject(responseEntity.getBody(), Map.class);
            if (MapUtil.getBool(responseMap,"success")) {
                // todo
                return "success";
            }
            return responseMap.get("error_msg");
        }
        return "system error";
    }

    @NotNull
    private static HttpHeaders getHttpHeaders(String sign, String companyName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("sign", sign);
        if (!StringUtils.isEmpty(companyName)) {
            headers.add("company_name", companyName);
        }
        return headers;
    }

    @NotNull
    private static Map getPushOrderParams(CDSPushOrder cdsPushOrder) {
        Map params = new HashMap(1024);
        params.put("company_name", cdsPushOrder.getCompanyName());
        params.put("order_id", cdsPushOrder.getOrderId());
        params.put("in_order_no", cdsPushOrder.getInOrderNo());
        params.put("net_weight", cdsPushOrder.getNetWeight());
        params.put("gross_weight", cdsPushOrder.getGrossWeight());
        params.put("purchaser_acount", cdsPushOrder.getPurchaserAcount());
        params.put("buyer_name", cdsPushOrder.getBuyerName());
        params.put("receiver_id", cdsPushOrder.getReceiverId());
        params.put("purchaser_tel", cdsPushOrder.getPurchaserTel());
        params.put("receiver_name", cdsPushOrder.getReceiverName());
        params.put("receiver_mobile", cdsPushOrder.getReceiverMobile());
        params.put("receiver_address", cdsPushOrder.getReceiverAddress());
        params.put("receiver_zip", cdsPushOrder.getReceiverZip());
        params.put("transport_service_code", cdsPushOrder.getTransportServiceCode());
        params.put("transport_order_id", cdsPushOrder.getTransportOrderId());
        params.put("order_goods_amount", cdsPushOrder.getOrderGoodsAmount());
        params.put("order_create_time", cdsPushOrder.getOrderCreateTime());
        params.put("need_insured_fee", cdsPushOrder.getNeedInsuredFee());
        params.put("consignee_prov", cdsPushOrder.getConsigneeProv());
        params.put("consignee_city", cdsPushOrder.getConsigneeCity());
        params.put("consignee_county", cdsPushOrder.getConsigneeCounty());
        params.put("consignee_town", cdsPushOrder.getConsigneeTown());
        params.put("consignee_detailed_address", cdsPushOrder.getConsigneeDetailedAddress());

        OrderFeeInfo feeInfo = cdsPushOrder.getFeeInfo();
        if (Objects.nonNull(feeInfo)) {
            params.put("fee_info", feeInfo);
        }

        OrderPayInfo payInfo = cdsPushOrder.getPayInfo();
        if (Objects.nonNull(payInfo)) {
            params.put("pay_info", payInfo);
        }

        List<OrderItem> orderItems = cdsPushOrder.getOrderItems();
        if (!CollectionUtils.isEmpty(orderItems)) {
            params.put("order_items", orderItems);
        }
        return params;
    }
}
