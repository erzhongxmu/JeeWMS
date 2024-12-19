package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/25 16:21
 * @description
 */
@Data
public class batchUpdateBinDTO {

    @ApiModelProperty("储位编码")
    private String kwCode;

    @ApiModelProperty("id数组")
    private List<String> id;
}
