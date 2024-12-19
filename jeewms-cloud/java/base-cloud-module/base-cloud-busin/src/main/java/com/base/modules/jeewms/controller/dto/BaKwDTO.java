package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/18 13:54
 * @description
 */
@Data
public class BaKwDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("N启用 Y停用")
    private String status;
}
