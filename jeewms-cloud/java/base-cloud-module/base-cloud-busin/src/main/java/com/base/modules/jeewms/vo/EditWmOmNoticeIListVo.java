package com.base.modules.jeewms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Describe : 批量修改出货通知项目
 * @Author: zly
 * @Create Date: 2021-05-18
 */
@Data
public class EditWmOmNoticeIListVo implements Serializable {
    private static final long serialVersionUID = -1314961268049085892L;

    @ApiModelProperty(value = "主键")
    private java.lang.String id;

    @ApiModelProperty(value = "出货数量")
    private java.lang.String goodsQua;

    @Excel(name = "生产日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "生产日期")
    private java.util.Date goodsProData;

    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;

    @ApiModelProperty(value = "imCusCode")
    private java.lang.String imCusCode;

    @ApiModelProperty(value = "托盘")
    private java.lang.String binId;

    @ApiModelProperty(value = "下架计划生成")
    private java.lang.String planSta;

    @Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateDate;
}
