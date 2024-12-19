package com.base.modules.jeewms.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

/**
 * @Describe 添加托盘转移入参
 * @Author zly
 * @Create Date 2021/5/26
 */
@Data
public class addWmToMoveVo implements Serializable {
    private static final long serialVersionUID = 1628310364324428882L;

    @ApiModelProperty(value = "到托盘")
    private java.lang.String tinId;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "转移数量")
    private java.lang.String transquas;


    //被分箱的
    /**长*/
    @TableField(exist = false)
    private String tinLength;
    /**宽*/
    @TableField(exist = false)
    private String tinWidth;
    /**高*/
    @TableField(exist = false)
    private String tinHigh;
    /**重量*/
    @TableField(exist = false)
    private String tinWeight;


    //分箱后的
    /**长*/
    @TableField(exist = false)
    private String tinLengths;
    /**宽*/
    @TableField(exist = false)
    private String tinWidths;
    /**高*/
    @TableField(exist = false)
    private String tinHighs;
    /**重量*/
    @TableField(exist = false)
    private String tinWeights;




}
