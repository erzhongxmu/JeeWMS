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

/**
 * @Description: ba_kw
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Data
@TableName("md_bin")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ba_kw对象", description="库位")
public class BaKw implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
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
    /**仓库*/
    @Excel(name = "仓库编码")
    @Dict(dictTable = "ba_store", dicText = "store_name", dicCode = "store_code")
    @ApiModelProperty(value = "仓库编码")
    @TableField("bin_store")
    private java.lang.String storeCode;
    /**仓库*/
//    @Excel(name = "仓库名称", width = 15)
//    @Dict(dictTable = "ba_store", dicText = "store_name", dicCode = "store_code")
//    @ApiModelProperty(value = "仓库")
//    @TableField("bin_store")
    private java.lang.String wareName;
    /**库区编码*/
    @Excel(name = "库区编码", width = 15)
    @ApiModelProperty(value = "库区编码")
    @Dict(dictTable = "ba_store_area",dicCode = "area_code",dicText = "area_name")
    private java.lang.String storeAreaCode;
	/**库位名称*/
	@Excel(name = "储位名称", width = 15)
    @ApiModelProperty(value = "储位")
    @TableField("ku_wei_ming_cheng")
    private java.lang.String kwName;
	/**库位编码*/
	@Excel(name = "库位编码", width = 15)
    @ApiModelProperty(value = "库位编码")
    @TableField("ku_wei_bian_ma")
    private java.lang.String kwCode;
	/**库位条码*/
//	@Excel(name = "库位条码", width = 15)
    @ApiModelProperty(value = "库位条码")
    @TableField("ku_wei_tiao_ma")
    private java.lang.String kwBarCode;
    /**是否混放*/
//    @Excel(name = "是否混放", width = 15)
    @ApiModelProperty(value = "是否混放")
    @Dict(dicCode = "is_or_not")
    private java.lang.String mixStore;
    /**动态库位*/
//    @Excel(name = "是否动态库位", width = 15)
    @ApiModelProperty(value = "动态库位")
    @Dict(dicCode = "is_or_not")
    private java.lang.String dynamicLocation;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    @TableField("ming_xi1")
    private java.lang.String attr1;
	/**库位类型*/
	@Excel(name = "库位类型", width = 15, dicCode = "kw_type")
	@Dict(dicCode = "kw_type")
    @ApiModelProperty(value = "库位类型")
    @TableField("ku_wei_lei_xing")
    private java.lang.String kwType;
	/**库位属性*/
	@Excel(name = "库位属性", width = 15, dicCode = "kw_attr")
	@Dict(dicCode = "kw_attr")
    @ApiModelProperty(value = "库位属性")
    @TableField("ku_wei_shu_xing")
    private java.lang.String kwAttr;
	/**上架次序*/
//	@Excel(name = "上架次序", width = 15)
    @ApiModelProperty(value = "上架次序")
    @TableField("shang_jia_ci_xu")
    private java.lang.String orderOn;
	/**取货次序*/
//	@Excel(name = "取货次序", width = 15)
    @ApiModelProperty(value = "取货次序")
    @TableField("qu_huo_ci_xu")
    private java.lang.String orderDowm;
	/**所属客户*/
//	@Excel(name = "所属客户", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "所属客户")
    @TableField("suo_shu_ke_hu")
    private java.lang.String custom;
	/**体积单位*/
//	@Excel(name = "体积单位", width = 15)
    @ApiModelProperty(value = "体积单位")
    @TableField("ti_ji_dan_wei")
    private java.lang.String volumeUnit;
	/**重量单位*/
//	@Excel(name = "重量单位", width = 15)
    @ApiModelProperty(value = "重量单位")
    @TableField("zhong_liang_dan_wei")
    private java.lang.String weightUnit;
	/**面积单位*/
//	@Excel(name = "面积单位", width = 15)
    @ApiModelProperty(value = "面积单位")
    @TableField("mian_ji_dan_wei")
    private java.lang.String areaUnit;
	/**最大体积*/
//	@Excel(name = "最大体积", width = 15)
    @ApiModelProperty(value = "最大体积")
    @TableField("zui_da_ti_ji")
    private java.lang.String maxVolume;
	/**最大重量*/
//	@Excel(name = "最大重量", width = 15)
    @ApiModelProperty(value = "最大重量")
    @TableField("zui_da_zhong_liang")
    private java.lang.String maxWeight;
	/**最大面积*/
//	@Excel(name = "最大面积", width = 15)
    @ApiModelProperty(value = "最大面积")
    @TableField("zui_da_mian_ji")
    private java.lang.String maxArea;
	/**最大托盘*/
//	@Excel(name = "最大托盘", width = 15)
    @ApiModelProperty(value = "最大托盘")
    @TableField("zui_da_tuo_pan")
    private java.lang.String maxTray;
	/**长度*/
//	@Excel(name = "长度", width = 15)
    @ApiModelProperty(value = "长度")
    @TableField("chang")
    private java.lang.String length;
	/**宽度*/
//	@Excel(name = "宽度", width = 15)
    @TableField("kuan")
    @ApiModelProperty(value = "宽度")
    private java.lang.String width;
	/**高度*/
//	@Excel(name = "高度", width = 15)
    @ApiModelProperty(value = "高度")
    @TableField("gao")
    private java.lang.String height;
	/**停用*/
	@Excel(name = "停用", width = 15)
    @ApiModelProperty(value = "停用")
    @TableField("ting_yong")
    private java.lang.String status;
	/**明细*/
	//@Excel(name = "明细", width = 15)
    @ApiModelProperty(value = "明细")
    @TableField("ming_xi")
    private java.lang.String detail;
	/**产品属性*/
//	@Excel(name = "产品属性", width = 15, dicCode = "product_attr")
	@Dict(dicCode = "product_attr")
    @ApiModelProperty(value = "产品属性")
    @TableField("CHP_SHU_XING")
    private java.lang.String productAttr;
	/**备注1*/
	//@Excel(name = "备注1", width = 15)
    @ApiModelProperty(value = "备注1")
    @TableField("ming_xi2")
    private java.lang.String attr2;
	/**备注2*/
	//@Excel(name = "备注2", width = 15)
    @ApiModelProperty(value = "备注2")
    @TableField("ming_xi3")
    private java.lang.String attr3;
	/**电子标签id*/
//	@Excel(name = "电子标签id", width = 15)
    @ApiModelProperty(value = "电子标签id")
    @TableField("LORA_bqid")
    private java.lang.String rfId;
	/**X坐标*/
	@Excel(name = "X坐标", width = 15)
    @ApiModelProperty(value = "X坐标")
    private java.lang.String xnode;
	/**Y坐标*/
	@Excel(name = "Y坐标", width = 15)
    @ApiModelProperty(value = "Y坐标")
    private java.lang.String ynode;
	/**Z坐标*/
	@Excel(name = "Z坐标", width = 15)
    @ApiModelProperty(value = "Z坐标")
    private java.lang.String znode;
    /**库位用途*/
//    @Excel(name = "库位用途", width = 15)
    @ApiModelProperty(value = "库位用途")
    @Dict(dicCode = "bin_use")
    private java.lang.String binUse;
    /**存放商品类型*/
//    @Excel(name = "存放商品类型",dictTable = "ba_part_type",dicCode = "type_name",dicText = "type_name",width = 15)
    @ApiModelProperty(value = "存放商品类型")
    private java.lang.String partType;
    /**租户*/
//    // @Excel(name = "租户", width = 15)
   /* @ApiModelProperty(value = "租户")
    private Integer tenantId;*/

    //备注
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remarks;

}
