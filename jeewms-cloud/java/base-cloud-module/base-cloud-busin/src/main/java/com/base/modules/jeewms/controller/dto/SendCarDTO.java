package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/19 14:05
 * @description
 */
@Data
public class SendCarDTO {

    @ApiModelProperty("订单id，多个用逗号隔开")
    private String id;

    @ApiModelProperty("司机名称")
    private String sj;

    @ApiModelProperty("车号")
    private String chehao;
}
