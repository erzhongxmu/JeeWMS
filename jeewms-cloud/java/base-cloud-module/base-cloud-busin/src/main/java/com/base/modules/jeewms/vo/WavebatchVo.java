package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WavebatchVo implements Serializable {
    private static final long serialVersionUID = -4502394267822002464L;

    @ApiModelProperty(value = "波次容器")
    private String firstRq;

    @ApiModelProperty(value = "主键")
    private List<String> ids;
}
