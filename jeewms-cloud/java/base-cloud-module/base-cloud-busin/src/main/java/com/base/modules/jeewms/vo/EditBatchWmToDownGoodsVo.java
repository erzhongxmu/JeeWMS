package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 下架明细批量更改（按单拣货批量修改）
 * @Author: zly
 * @Date:   2021-05-21
 * @Version: V1.0
 */
@Data
public class EditBatchWmToDownGoodsVo implements Serializable {
    private static final long serialVersionUID = 3792494755206643673L;

    @ApiModelProperty(value = "主键")
    private java.lang.String id;

    @Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;

    @Excel(name = "源托盘码", width = 15)
    @ApiModelProperty(value = "源托盘码")
    private java.lang.String binIdFrom;

    @Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String goodsProData;

    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.String goodsQua;

    @Excel(name = "完成数量", width = 15)
    @ApiModelProperty(value = "完成数量")
    private java.lang.String goodsQuaok;
}
