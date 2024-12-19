package com.base.common.base.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author JiWang
 * @PROJECT zdwms
 * @Description
 * @Date 2021/11/5 14:21
 **/
@Data
public class WmsOrderCallbackDTO {

    /**
     * 订单号
     */
    private String	orderId;
    /**
     * 订单状态
     */
    private Integer	orderStatus;
    /**
     * 包裹重量
     */
    private Integer	weight;
    /**
     * 快递公司
     */
    private String expressName;
    /**
     * 运单号
     */
    private String deliveryId;
    /**
     * 实际操作时间
     */
    private LocalDateTime optTime;

    /**
     * 订单详情信息
     */
    List<WmsOrderItemCallbackDTO> itemCallbackDTOS;

}
