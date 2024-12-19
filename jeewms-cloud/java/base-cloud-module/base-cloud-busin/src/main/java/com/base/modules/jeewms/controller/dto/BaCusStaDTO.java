package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/17 13:57
 * @description
 */
@Data
public class BaCusStaDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("0启用 1停用")
    private String cusStaDel;
}
