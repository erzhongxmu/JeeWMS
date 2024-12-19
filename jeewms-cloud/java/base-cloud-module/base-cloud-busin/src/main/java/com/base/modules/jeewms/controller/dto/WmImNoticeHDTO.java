package com.base.modules.jeewms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/21 16:29
 * @description
 */
@Data
public class WmImNoticeHDTO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**创建人名称*/
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
    /**创建人名称*/
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createBy;
    /**创建日期*/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**更新人名称*/
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
    /**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
    /**更新日期*/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**所属公司*/
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
    /**客户编码*/
    @ApiModelProperty(value = "客户编码")
    private java.lang.String cusCode;
    /**预计到货时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预计到货时间")
    private java.util.Date imData;
    /**客户订单号*/
    @ApiModelProperty(value = "客户订单号")
    private java.lang.String imCusCode;
    /**司机*/
    @ApiModelProperty(value = "司机")
    private java.lang.String imCarDri;
    /**司机电话*/
    @ApiModelProperty(value = "司机电话")
    private java.lang.String imCarMobile;
    /**车号*/
    @ApiModelProperty(value = "车号")
    private java.lang.String imCarNo;
    /**订单类型*/
    @ApiModelProperty(value = "订单类型(其他入库09 )")
    private java.lang.String orderTypeCode;
    /**月台*/
    @ApiModelProperty(value = "月台")
    private java.lang.String platformCode;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private java.lang.String imBeizhu;
    /**单据状态*/
    @ApiModelProperty(value = "单据状态")
    private java.lang.String imSta;
    /**进货通知单号*/
    @ApiModelProperty(value = "进货通知单号")
    private java.lang.String noticeId;
    /**附件*/
    @ApiModelProperty(value = "附件")
    private java.lang.String fuJian;
    /**readOnly*/
    @ApiModelProperty(value = "readOnly")
    private java.lang.String readOnly;
    /**whereCon*/
    @ApiModelProperty(value = "whereCon")
    private java.lang.String whereCon;
    /**供应商编码*/
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supCode;
    /**供应商名称*/
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supName;
    /**对接单据类型*/
    @ApiModelProperty(value = "对接单据类型")
    private java.lang.String piClass;
    /**账套*/
    @ApiModelProperty(value = "账套")
    private java.lang.String piMaster;

//    @ApiModelProperty(value = "商品")
//    @NotEmpty(message = "商品为空")
//    private List<WmImNoticeIDTO> goodsList ;

}
