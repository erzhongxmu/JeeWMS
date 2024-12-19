package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InsertAssignToBatchVo implements Serializable {
    private static final long serialVersionUID = -1856259027486287269L;

    @ApiModelProperty(value = "任务接收人")
    private java.lang.String assignTo;

    @ApiModelProperty(value = "主键")
    private List<String> ids;
}
