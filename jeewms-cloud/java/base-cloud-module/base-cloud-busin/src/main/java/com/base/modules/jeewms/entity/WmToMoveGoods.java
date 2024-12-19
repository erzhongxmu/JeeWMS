package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

/**
 * @Description: 库存转移
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
@Data
@TableName("wm_to_move_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_to_move_goods对象", description="库存转移")
public class WmToMoveGoods implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人名称*/
	//// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
	//// @Excel(name = "更新人名称", width = 15)
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
	//// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
	/**原始单据类型*/
	//@Excel(name = "原始单据类型", width = 15)
    @ApiModelProperty(value = "原始单据类型")
    private java.lang.String orderTypeCode;
	/**原始单据编码*/
	//@Excel(name = "原始单据编码", width = 15)
    @ApiModelProperty(value = "原始单据编码")
    private java.lang.String orderId;
	/**原始单据行项目*/
	//@Excel(name = "原始单据行项目", width = 15)
    @ApiModelProperty(value = "原始单据行项目")
    private java.lang.String orderIdI;
	/**商品编码*/
	//@Excel(name = "商品编码", width = 15)
    //@ApiModelProperty(value = "商品编码")
    @Excel(name = "商品编码", width = 15,orderNum = "1")
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
	/**商品名称*/
	//@Excel(name = "商品名称", width = 15)
    //@ApiModelProperty(value = "商品名称")
    @Excel(name = "商品名称", width = 15,orderNum = "2")
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.String goodsQua;
	/**生产日期*/
	//@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String goodsProData;
	/**单位*/
	//@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String goodsUnit;
	/**客户编码*/
	//@Excel(name = "客户编码", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	//@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    //@ApiModelProperty(value = "客户编码")
    @Excel(name = "供应商编码", width = 15,orderNum = "3")
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "客户编码")
    private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name = "供应商名称", width = 15,orderNum = "4")
    @ApiModelProperty(value = "客户名称")
    private java.lang.String cusName;
	/**源托盘*/
	//@Excel(name = "源托盘", width = 15)
    @ApiModelProperty(value = "源托盘")
    private java.lang.String tinFrom;
	/**到托盘*/
	@Excel(name = "到托盘", width = 15,orderNum = "6")
    @ApiModelProperty(value = "到托盘")
    private java.lang.String tinId;
	/**源库位*/
    //@Excel(name = "源库位", width = 15, dictTable = "md_bin", dicText = "ku_wei_ming_cheng", dicCode = "ku_wei_bian_ma")
    @Dict(dictTable = "md_bin", dicText = "ku_wei_ming_cheng", dicCode = "ku_wei_bian_ma")
    @ApiModelProperty(value = "源库位")
    private java.lang.String binFrom;
	/**到库位*/
    @Excel(name = "到库位", width = 15,orderNum = "5")
    @Dict(dictTable = "md_bin", dicText = "ku_wei_ming_cheng", dicCode = "ku_wei_bian_ma")
    @ApiModelProperty(value = "到库位")
    private java.lang.String binTo;
	/**状态*/
	//@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String moveSta;
	/**转移客户*/
	//@Excel(name = "转移客户", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "转移客户")
    private java.lang.String toCusCode;
	/**转移客户名称*/
	//@Excel(name = "转移客户名称", width = 15)
    @ApiModelProperty(value = "转移客户名称")
    private java.lang.String toCusName;
	/**基本单位*/
	//@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;
	/**基本单位数量*/
	@Excel(name = "数量", width = 15,orderNum = "7")
    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;
	/**到生产日期*/
	//@Excel(name = "到生产日期", width = 15)
    @ApiModelProperty(value = "到生产日期")
    private java.lang.String toGoodsProData;
	/**执行状态*/
	//@Excel(name = "执行状态", width = 15)
    @ApiModelProperty(value = "执行状态")
    private java.lang.String runSta;

    /**批次*/
    @Excel(name = "批次", width = 15)
    @ApiModelProperty(value = "批次")
    private java.lang.String goodsBatch;
    /**批次*/
    @Excel(name = "到批次", width = 15)
    @ApiModelProperty(value = "到批次")
    private java.lang.String toGoodsBatch;

    /**转出仓库代码*/
    @Excel(name = "转出仓库代码", width = 15)
    @ApiModelProperty(value = "转出仓库代码")
    private java.lang.String storeCodeFrom;
    /**转出仓库名称*/
    @Excel(name = "转出仓库名称", width = 15)
    @ApiModelProperty(value = "转出仓库名称")
    private java.lang.String storeNameFrom;

    /**转入仓库代码*/
    @Excel(name = "转入仓库代码", width = 15)
    @ApiModelProperty(value = "转入仓库代码")
    private java.lang.String storeCodeTo;
    /**转入仓库名称*/
    @Excel(name = "转入仓库名称", width = 15)
    @ApiModelProperty(value = "转入仓库名称")
    private java.lang.String storeNameTo;

    private String noticeId;
    private String u8Id;
    private String u8Sta;
    /**租户*/
    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;

    private String pareId;
}
