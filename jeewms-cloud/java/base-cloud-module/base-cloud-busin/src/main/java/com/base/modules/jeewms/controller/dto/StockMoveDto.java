package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/7/8 15:22
 * @description
 */
@Data
public class StockMoveDto {

    private String id;

    @ApiModelProperty("库位")
    private String kwCode;

    @ApiModelProperty("托盘")
    private String tinCode;

    @ApiModelProperty("数量")
    private String num;
}
