package com.base.modules.jeewms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Package com.base.modules.jeewms.controller.dto
 * @date 2021/5/28 19:49
 * @description
 */
@Data
public class WmOmNoticeHDTO {

    /**主键*/
    @ApiModelProperty("商品id")
    private String id;
    /**创建人名称*/

    private String createName;
    /**创建人登录名称*/

    private String createBy;
    /**创建日期*/

    private Date createDate;
    /**更新人名称*/

    private String updateName;
    /**更新人登录名称*/

    private String updateBy;
    /**更新日期*/

    private Date updateDate;
    /**所属部门*/


    private String readonly;
    private String wherecon;

    private String sysOrgCode;
    /**所属公司*/
    @ApiModelProperty("所属公司")
    private String sysCompanyCode;
    /**客户编码*/
    @Excel(name="客户编码")
    @ApiModelProperty("客户编码")
    private String cusCode;
    /**预计到货时间*/
    @Excel(name="预计出货时间",format = "yyyy-MM-dd")
    @ApiModelProperty("预计出货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date imData;
    /**客户订单号*/
    @Excel(name="客户订单号")
    @ApiModelProperty("客户订单号")
    private String imCusCode;

    @Excel(name="订单类型")
    @ApiModelProperty("订单类型")
    private String orderTypeCode;

    @Excel(name="备注")
    @ApiModelProperty("备注")
    private String imBeizhu;
    @Excel(name="收货人")
    @ApiModelProperty("收货人")
    private java.lang.String delvMember;
    /**收货人电话*/
    @Excel(name="收货人电话")
    @ApiModelProperty("收货人电话")
    private java.lang.String delvMobile;
    @Excel(name="收货地址")
    @ApiModelProperty("收货地址")
    private String delvAddr;





    /**承运人*/
    @Excel(name="运输公司")
    @ApiModelProperty("运输公司")
    private java.lang.String reMember;

    /**承运人车号*/
    @Excel(name="发货运单号")
    @ApiModelProperty("发货运单号")
    private java.lang.String reCarno;



    /**进货通知单号*/
//    @Excel(name="通知单号")
    private String noticeId;
    //	@Excel(name="合作伙伴编号")
    private String supCode;
    /**附件*/
//	@Excel(name="合作伙伴名称")
    private String supName;
    //	@Excel(name="其他系统ID")
    private String otherId;
    @Excel(name="商品")
    @ApiModelProperty("商品")
    private String goodsId;

    @Excel(name="商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**出货数量*/
    @Excel(name="数量")
    @ApiModelProperty("数量")
    private String goodsQua;
    /**出货单位*/
    @Excel(name="单位")
    @ApiModelProperty("单位")
    private String goodsUnit;


    //	@Excel(name="储位")
    private String binOm;

    //	@Excel(name="托盘")
    private String binId;


    //	@Excel(name="生产日期")
    private String goodsProData;
}
