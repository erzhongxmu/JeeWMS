package com.base.modules.jeewms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/25 16:30
 * @description
 */
@Data
public class BatchUpdateProduceDateDTO {

    @ApiModelProperty("生产日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date produceDate;

    @ApiModelProperty("id数组")
    private List<String> id;
}
