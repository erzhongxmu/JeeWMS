package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class WmsOrderItemCallbackDTO {

    private String skuId;

    private String batchNo;

    private BigDecimal costPrice;
}
