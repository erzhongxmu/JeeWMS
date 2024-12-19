package com.base.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WMSStockQueryDTO implements Serializable {
    /**
     * 商城对应的供应商编码
     */
    private String cusCode;
    /**
     * skuId
     */
    private List<String> sku_ids;
}