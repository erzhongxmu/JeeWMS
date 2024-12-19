package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @Describe : 批量修改库内转移入参
 * @Author: zly
 * @Create Date: 2021-05-26
 */
@Data
public class EditBatchWmToMoveGoodsVo implements Serializable {

    private static final long serialVersionUID = -4967879031868517375L;


    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "移动数量")
    private String baseGoodscount;

    @ApiModelProperty(value = "到托盘")
    private String tinId;

    @ApiModelProperty(value = "到储位")
    private String binTo;
}
