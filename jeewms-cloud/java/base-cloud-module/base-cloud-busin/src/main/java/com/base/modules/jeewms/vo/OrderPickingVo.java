package com.base.modules.jeewms.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

@Data
public class OrderPickingVo implements Serializable {

    private static final long serialVersionUID = 4657737560545089127L;

    /**主键*/
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**数量*/
    @ApiModelProperty(value = "数量")
    private java.lang.String goodsQua;
    /**完成数量*/
    @ApiModelProperty(value = "完成数量")
    private java.lang.String goodsQuaok;
}
