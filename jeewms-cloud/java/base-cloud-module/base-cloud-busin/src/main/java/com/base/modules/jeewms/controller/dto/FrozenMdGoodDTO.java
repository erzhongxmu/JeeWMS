package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/6/17 10:05
 * @description
 */
@Data
public class FrozenMdGoodDTO {

    @ApiModelProperty("id")
    @NotBlank(message = "id为空")
    private String id;

    @ApiModelProperty("冻结Y 解冻N")
    @NotBlank(message = "冻结状态为空")
    private String frozen;
}
