package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/19 15:41
 * @description
 */
@Data
public class HdDTO {

    @ApiModelProperty(value = "id")
    private List<String> id;
    /**回单备注*/
    @ApiModelProperty(value = "回单备注")
    private java.lang.String ywhdbz;
    /**运费*/
    @ApiModelProperty(value = "运费")
    private java.lang.String hwyf;
    /**货物总费用*/
    @ApiModelProperty(value = "货物总费用")
    private java.lang.String hwzfy;
    /**卸货费*/
    @ApiModelProperty(value = "卸货费")
    private java.lang.String hwxhf;

}
