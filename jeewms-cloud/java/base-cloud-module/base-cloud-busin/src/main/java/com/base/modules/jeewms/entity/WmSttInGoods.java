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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: wm_stt_in_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
@Data
@TableName("wm_stt_in_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_stt_in_goods对象", description="wm_stt_in_goods")
public class WmSttInGoods implements Serializable {
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
	/**库位编码*/
	@Excel(name = "库位编码", width = 15)
    @ApiModelProperty(value = "库位编码")
    private java.lang.String binId;
	/**托盘编码*/
	@Excel(name = "托盘编码", width = 15)
    @ApiModelProperty(value = "托盘编码")
    private java.lang.String tinId;
	/**商品编码*/
	//@Excel(name = "商品编码", width = 15)
    //@ApiModelProperty(value = "商品编码")
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
	/**商品名称*/
	//@Excel(name = "商品名称", width = 15)
    //@ApiModelProperty(value = "商品名称")
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.String goodsQua;
	/**单位*/
	//@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String goodsUnit;
	/**生产日期*/
	@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String goodsProData;
	/**批次*/
	//@Excel(name = "批次", width = 15)
    @ApiModelProperty(value = "批次")
    private java.lang.String goodsBatch;
	/**盘点数量*/
	//@Excel(name = "盘点数量", width = 15)
    @ApiModelProperty(value = "盘点数量")
    private java.lang.String sttQua;
	/**客户名称*/
	//@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String cusName;
	/**客户*/
	//@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String cusCode;
	/**盘点状态*/
	@Excel(name = "盘点状态", width = 15)
    @ApiModelProperty(value = "盘点状态")
    private java.lang.String sttSta;
	/**基本单位*/
	//@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;
	/**基本单位数量*/
	//@Excel(name = "基本单位数量", width = 15)
    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;
	/**sttId*/
	//@Excel(name = "sttId", width = 15)
    @ApiModelProperty(value = "sttId")
    private java.lang.String sttId;
	/**商品统一编码*/
	//@Excel(name = "商品统一编码", width = 15)
    @ApiModelProperty(value = "商品统一编码")
    private java.lang.String goodsCode;
	/**盘点类型*/
	//@Excel(name = "盘点类型", width = 15)
    @ApiModelProperty(value = "盘点类型")
    private java.lang.String sttType;
	/**动线*/
	//@Excel(name = "动线", width = 15)
    @ApiModelProperty(value = "动线")
    private java.lang.String dongXian;

	@TableField(exist = false)
	private  String areaCode;
    @TableField(exist = false)
    private  String kwName;

    @ApiModelProperty(value = "实际数量")
    private String realQua;

    @ApiModelProperty(value = "差异数量")
    private String diffQua;

    /**仓库代码*/
    @Excel(name = "仓库代码", width = 15)
    @ApiModelProperty(value = "仓库代码")
    private java.lang.String storeCode;
    /**仓库名称*/
    @Excel(name = "仓库名称", width = 15)
    @ApiModelProperty(value = "仓库名称")
    private java.lang.String storeName;

    private String noticeId;
    private String u8Id;
    private String u8Code;
    private String u8Sta;
    /**租户*/
    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;
}
