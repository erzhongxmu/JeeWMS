package com.base.modules.jeewms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class EditBatchWmOmNoticeHVo implements Serializable {
    private static final long serialVersionUID = 2948372502016818228L;

    @ApiModelProperty(value = "主键")
    private java.lang.String id;

    @Excel(name = "要求交货时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "要求交货时间")
    private java.util.Date delvData;

    @ApiModelProperty(value = "备注")
    private java.lang.String omBeizhu;
}
