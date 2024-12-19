package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 下架批量修改入参
 */
@Data
public class EditBatchWmQmIVo implements Serializable {
    private static final long serialVersionUID = -9056567125409401561L;

    @ApiModelProperty(value = "主键")
    private java.lang.String id;

    @ApiModelProperty(value = "数量")
    private java.lang.String qmOkQuat;

    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;

    @ApiModelProperty(value = "生产日期")
    private java.lang.String proData;

    @ApiModelProperty(value = "托盘")
    private java.lang.String tinId;

    @ApiModelProperty(value = "仓位")
    private java.lang.String binId;
}
