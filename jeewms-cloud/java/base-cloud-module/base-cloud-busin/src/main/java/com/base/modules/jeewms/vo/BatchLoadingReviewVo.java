package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Describe 下架明细-装车复核-批量复核入参
 * @Author zly
 * @Create Date 2021/5/21
 */
@Data
public class BatchLoadingReviewVo implements Serializable {
    private static final long serialVersionUID = 9018499859197916529L;

    @ApiModelProperty(value = "主键")
    private java.lang.String id;

    @ApiModelProperty(value = "完成数量")
    private java.lang.String goodsQuaok;
}
