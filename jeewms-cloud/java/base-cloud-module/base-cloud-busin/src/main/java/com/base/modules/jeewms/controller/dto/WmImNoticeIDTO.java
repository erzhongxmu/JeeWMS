package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/21 16:34
 * @description
 */
@Data
public class WmImNoticeIDTO {

    /**商品编码*/
    @ApiModelProperty(value = "商品编码")
    private String goodsCode;
    /**数量*/
    @ApiModelProperty(value = "数量")
    private String goodsCount;
    /**库位整理*/
    @ApiModelProperty(value = "库位整理")
    private String binPre;
    /**计划库位*/
    @ApiModelProperty(value = "计划库位")
    private String binPlan;
}
