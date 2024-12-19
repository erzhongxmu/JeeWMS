package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/17 11:43
 * @description 启/停用 企业属性
 */
@Data
public class ComTypeDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("0启用 1停用")
    private String comTypeDel;
}
