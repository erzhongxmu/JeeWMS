package com.base.modules.jeewms.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/20 14:27
 * @description
 */
@Data
public class KwPicDataDTO {

    /**库位编码*/
    @ApiModelProperty(value = "库位编码")
    private java.lang.String kwCode;

    @ApiModelProperty(value = "仓库")
    private java.lang.String storeCode;

    @ApiModelProperty(value = "其他")
    private String des;

    @ApiModelProperty(value = "行数")
    private java.lang.String rowNum;

    @ApiModelProperty(value = "层数")
    private java.lang.String znode;

    @ApiModelProperty("排列顺序 0正向 1反向")
    private String type;
}
