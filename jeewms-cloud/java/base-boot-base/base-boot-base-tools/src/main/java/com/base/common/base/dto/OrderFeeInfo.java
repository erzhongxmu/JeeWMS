package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderFeeInfo {

    /**
     * 税费
     */
    private BigDecimal tax;

    /**
     * 运费
     */
    private BigDecimal	freight;

    /**
     * 保费
     */
    private BigDecimal	insurance;

    /**
     * 折扣费用
     */
    private BigDecimal	deduction_amount;
}
