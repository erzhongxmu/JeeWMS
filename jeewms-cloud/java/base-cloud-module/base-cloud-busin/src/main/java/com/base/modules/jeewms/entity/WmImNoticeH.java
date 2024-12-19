package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.base.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: wm_im_notice_h
 * @Author: base-boot
 * @Date:   2021-05-21
 * @Version: V1.0
 */
@Data
@TableName("wm_im_notice_h")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_im_notice_h对象", description="进货")
public class WmImNoticeH implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人名称*/
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
	/**创建人名称*/
    // @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createBy;
	/**创建日期*/
    @Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**所属公司*/
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
	/**客户编码*/
	@Excel(name = "客户编码", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "客户编码")
    private java.lang.String cusCode;
	/**预计到货时间*/
	@Excel(name = "预计到货时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "预计到货时间")
    private java.util.Date imData;
	/**第三方客户编码*/
//	@Excel(name = "第三方客户编码", width = 15)
    @ApiModelProperty(value = "第三方客户编码")
    private java.lang.String imCusCode;
	/**司机*/
//    @Excel(name = "司机", width = 15)
    @ApiModelProperty(value = "司机")
    private java.lang.String imCarDri;
	/**司机电话*/
//    @Excel(name = "司机电话", width = 15)
    @ApiModelProperty(value = "司机电话")
    private java.lang.String imCarMobile;
	/**车号*/
    @ApiModelProperty(value = "车号")
    private java.lang.String imCarNo;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15, dictTable = "ba_order_type", dicText = "order_type_name", dicCode = "order_type_code")
	@Dict(dictTable = "ba_order_type", dicText = "order_type_name", dicCode = "order_type_code")
    @ApiModelProperty(value = "订单类型(其他入库09 )")
    private java.lang.String orderTypeCode;
	/**月台*/
//	@Excel(name = "月台", width = 15, dictTable = "ba_platform", dicText = "platform_name", dicCode = "platform_code")
	@Dict(dictTable = "ba_platform", dicText = "platform_name", dicCode = "platform_code")
    @ApiModelProperty(value = "月台")
    private java.lang.String platformCode;
	/**备注*/
//	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String imBeizhu;
	/**单据状态*/
	@Excel(name = "单据状态", width = 15)
    @ApiModelProperty(value = "单据状态")
    private java.lang.String imSta;
	/**进货通知单号*/
	@Excel(name = "进货通知单号", width = 15)
    @ApiModelProperty(value = "进货通知单号")
    private java.lang.String noticeId;
	/**附件*/
//	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String fuJian;
	/**readOnly*/
//	@Excel(name = "readOnly", width = 15)
    @ApiModelProperty(value = "readOnly")
    private java.lang.String readOnly;
	/**whereCon*/
//	@Excel(name = "whereCon", width = 15)
    @ApiModelProperty(value = "whereCon")
    private java.lang.String whereCon;
	/**供应商编码*/
	@Excel(name = "供应商编码", width = 15, dictTable = "md_sup", dicText = "zhong_wen_qch", dicCode = "gys_bian_ma")
	@Dict(dictTable = "md_sup", dicText = "zhong_wen_qch", dicCode = "gys_bian_ma")
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supCode;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supName;
	/**对接单据类型*/
	@Excel(name = "对接单据类型", width = 15)
//    @ApiModelProperty(value = "对接单据类型")
    private java.lang.String piClass;
	/**账套*/
	@Excel(name = "账套", width = 15)
//    @ApiModelProperty(value = "账套")
    private java.lang.String piMaster;

    /**税率*/
    @Excel(name = "税率", width = 15)
//    @ApiModelProperty(value = "税率")
    private java.lang.String taxRate;

    @ApiModelProperty(value = "是否加急")
    private java.lang.String urgent="N";

    /**仓库代码*/
//    @Excel(name = "仓库代码", width = 15)
    @ApiModelProperty(value = "仓库代码")
    private java.lang.String storeCode;
    /**仓库名称*/
//    @Excel(name = "仓库名称", width = 15)
    @ApiModelProperty(value = "仓库名称")
    private java.lang.String storeName;


    @TableField(exist = false)
    List<WmImNoticeI> wmImNoticeIS;

    private String orderType;
    //门店退货入库、配送差异入库
//    @Excel(name = "入库类型", width = 15)
    @ApiModelProperty(value = "入库类型")
    private String orderTypes;
    private String cusName;
    private String u8Id;
    private String u8Dhcode;
    private String u8Cgcode;

    @Excel(name = "销售单号", width = 15)
    private String u8ReturnCode;
    private String u8Sta;
    /**租户*/
//    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;

    //国声字段
//    @Excel(name = "仓管员", width = 15)
    @ApiModelProperty(value = "仓管员")
    private String fstockername;

    @Excel(name = "跟单", width = 15)
    @ApiModelProperty(value = "跟单")
    private String purchasename;



    @Excel(name = "客户编号", width = 15)
    @ApiModelProperty(value = "客户编号")
    @Dict(dictTable = "md_cus_other", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    private String imclientcode;

    @ApiModelProperty(value = "备用字段")
    private String association;

    //内部发票号
    @Excel(name = "内部发票号", width = 15)
    @ApiModelProperty(value = "内部发票号")
    private String astreanum;
//   plqn跟单员 private String purchasename;

    /**单据备注*/
//	@Excel(name = "仓库名称", width = 15)
    @ApiModelProperty(value = "单据备注")
    private java.lang.String orderRemark;

}
